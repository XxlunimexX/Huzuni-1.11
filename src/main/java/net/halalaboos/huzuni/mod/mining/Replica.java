package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.RenderManager.Renderer;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.task.PlaceTask;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.huzuni.api.util.gl.Box;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.huzuni.mod.mining.templates.*;
import net.halalaboos.mcwrapper.api.event.input.MouseEvent;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.enums.MouseButton;
import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.halalaboos.mcwrapper.api.util.math.Result;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Places blocks based on templates.
 *
 * TODO: Port to MCWrapper
 * */
public final class Replica extends BasicMod implements Renderer {

	private final PlaceTask placeTask = new PlaceTask(this);

	public final Value placeDelay = new Value("Place delay", " ms", 0F, 100F, 500F, 5F, "Delay in MS between placing of each block");

	public final Toggleable silent = new Toggleable("Silent", "Adjust player rotation server-sided only");

	public final Mode<Template> mode = new Mode<Template>("Template", "Select which template will be used", new WallsTemplate(), new RectangleTemplate(), new CylinderTemplate(), new SwastikaTemplate(), new PortalTemplate(), new PenisTemplate()) {

		@Override
		public void setSelectedItem(int selectedItem) {
			super.setSelectedItem(selectedItem);
			templateBuilder.setTemplate(getSelectedItem());
		}
	};

	private final TemplateBuilder templateBuilder = new TemplateBuilder();

	private final Box box;

	// TODO: sphere, tunnel, stairs
	public Replica() {
		super("Replica", "Replicates blocks from a template");
		addChildren(placeDelay, silent, mode);
		setAuthor("Halalaboos");
		setCategory(Category.MINING);
		silent.setEnabled(true);
		templateBuilder.setTemplate(mode.getSelectedItem());
		huzuni.lookManager.registerTaskHolder(this);
		placeTask.setNaturalPlacement(false);
		box = new Box(new AABB(0F, 0F, 0F, 1F, 1F, 1F), true);
		huzuni.commandManager.addCommand(new BasicCommand("template", "loads templates from text documents") {

			@Override
			public void giveHelp() {
				huzuni.addChatMessage(".template \"file\"");
			}

			@Override
			protected void runCommand(String input, String[] args) throws Exception {
				File file = new File(huzuni.getSaveFolder(), args[0]);
				try {
					BasicTemplate template = BasicTemplate.readTemplate(file);
					if (template != null) {
						mode.add(template);
						huzuni.addChatMessage("Template loaded!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		subscribe(MouseEvent.class, this::onMouseClicked);
		subscribe(PreMotionUpdateEvent.class, this::onUpdate);
	}

	@Override
	public void onEnable() {
		huzuni.renderManager.addWorldRenderer(this);
		huzuni.addNotification(NotificationType.INFO, this, 5000, "Right-click a block to begin building a template!");
	}

	@Override
	public void onDisable() {
		huzuni.renderManager.removeWorldRenderer(this);
		huzuni.lookManager.withdrawTask(placeTask);
		templateBuilder.resetPositions();
	}

	private void onUpdate(PreMotionUpdateEvent event) {
		if (placeTask.hasBlock() && placeTask.isWithinDistance() && !placeTask.shouldResetBlock()) {
			placeTask.setPlaceDelay((int) placeDelay.getValue());
			placeTask.setReset(silent.isEnabled());
			huzuni.lookManager.requestTask(this, placeTask);
		} else {
			huzuni.lookManager.withdrawTask(placeTask);
			Vector3i closestPosition = null;
			Face closestFace = null;
			double closestDistance = 0;
			for (int i = 0; i < templateBuilder.getPositions().size(); i++) {
				Vector3i position = templateBuilder.getPositions().get(i);
				double distance = getPlayer().getDistanceTo(position);
				if (distance < getController().getBlockReach() && !getPlayer().getBlockPosition().equals(position)) {
					Face face = MinecraftUtils.getAdjacent(position);
					if (face != null) {
						if (closestPosition != null) {
							if (distance < closestDistance) {
								closestPosition = position;
								closestDistance = distance;
								closestFace = face;
							}
						} else {
							closestPosition = position;
							closestDistance = distance;
							closestFace = face;
						}
					}
				}
			}
			if (closestPosition != null) {
				Vector3i pos = closestPosition.offset(closestFace);
				Face face = closestFace.getOppositeFace();
				placeTask.setBlock(pos, face);
				placeTask.setPlaceDelay((int) placeDelay.getValue());
				placeTask.setReset(silent.isEnabled());
				huzuni.lookManager.requestTask(this, placeTask);
			} else
				placeTask.setBlock(null, null);
		}
	}

	private void onMouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.RIGHT) {
			Optional<Result> result = mc.getMouseResult();
			if (result.isPresent()) {
				if (result.get() == Result.BLOCK) {
					if (!templateBuilder.hasPositions()) {
						templateBuilder.addSelection(mc.getMouseVector(), mc.getMouseFace()	);
					}
				}
			}
		}
	}

	@Override
	public String getDisplayNameForRender() {
		return settings.getDisplayName() + (templateBuilder.hasPositions() ? " (" + templateBuilder.getPositions().size() + ")" : "");
	}

	@Override
	public void render(float partialTicks) {
		if (!templateBuilder.hasPositions()) {
			if (templateBuilder.canPreview() && mc.getMouseResult().isPresent() && mc.getMouseResult().get() == Result.BLOCK) {
				renderPreview(mc.getMouseVector(), mc.getMouseFace());
			}
		} else {
			for (int i = 0; i < templateBuilder.getPositions().size(); i++) {
				Vector3i position = templateBuilder.getPositions().get(i);
				if (getWorld().blockExists(position)) {
					templateBuilder.getPositions().remove(position);
					if (templateBuilder.getPositions().isEmpty()) {
						huzuni.addNotification(NotificationType.CONFIRM, this, 5000, "Finished!");
					}
					continue;
				}
				renderBox(position);
			}
		}
	}

	/**
	 * Renders a preview of the current template with the next position being the position provided.
	 * */
	public void renderPreview(Vector3i position, Face face) {
		List<Vector3i> previewPositions = templateBuilder.getPreview(position, face);
		for (Vector3i previewPosition : previewPositions) {
			renderBox(previewPosition);
		}
	}

	private void renderBox(Vector3i position) {
		renderBox(position.getX(), position.getY(), position.getZ());
	}

	private void renderBox(int x, int y, int z) {
		float renderX = (float) (x - mc.getCamera().getX());
		float renderY = (float) (y - mc.getCamera().getY());
		float renderZ = (float) (z - mc.getCamera().getZ());
		getGLStateManager().pushMatrix();
		getGLStateManager().translate(renderX, renderY, renderZ);
		GLUtils.glColor(1F, 1F, 1F, 0.2F);
		box.render();
		getGLStateManager().popMatrix();
	}

	/*
    Template building code that allowed the user to design their own pattern. It is possible to change the center for the template and other aspects of it etc. but this is not implemented for the user.

    public final ItemSelector<Integer> blockSelector = new ItemSelector<>("Custom Template", "great googly moogly");

    // This goes into the constructor.

    // ItemStack item = new ItemStack(Blocks.DIRT);
    //    for (int i = 0; i < 30; i++)
    //        blockSelector.addItem(item, i);

    private class CustomTemplate implements Template {

        @Override
        public String getName() {
            return "Custom";
        }

        @Override
        public String getDescription() {
            return "Build your own template!";
        }

        @Override
        public int getMaxPoints() {
            return 1;
        }

        @Override
        public String getPointName(int point) {
            return "Point " + (point + 1);
        }

        @Override
        public boolean insideBlock(BlockPos position) {
            return false;
        }

        @Override
        public void generate(List<BlockPos> outputPositions, EnumFacing face, BlockPos... positions) {
            int xOffset = face.rotateY().getDirectionVec().getX();
            int zOffset = face.rotateY().getDirectionVec().getZ();
            Integer[] vertices = generate(2, 4);
            BlockPos origin = positions[0];
            for (int i = 0; i < vertices.length; i += 2) {
                outputPositions.add(origin.add(xOffset * vertices[i], vertices[i + 1], zOffset * vertices[i]));
            }
        }

        // Generated the vertices from the center x and y positions (generated using the function below)
        private Integer[] generate(int centerX, int centerY) {
            List<Integer> vertices = new ArrayList<>();
            for (int i = 0; i < blockSelector.getItemDatas().size(); i++) {
                ItemSelector.ItemData itemData = blockSelector.getItemDatas().get(i);
                if (itemData.isEnabled()) {
                    vertices.add((i % 6) - centerX);
                    vertices.add(-((i / 6) - centerY));
                }
            }
            return vertices.toArray(new Integer[vertices.size()]);
        }

        // Generates the center positions needed for the index provided (I hope it does at least. The y position generated may need to be y - 1.)
        private Integer[] generateCenter(int index) {
            return new Integer[] { index % 6, index / 6 };
        }
    }
    */
}
