package net.halalaboos.huzuni.api.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.huzuni.api.node.Node;

import java.io.IOException;
import java.util.*;

/**
 * Manages tasks which can be chosen to run based on priority.
 * */
public class NewTaskManager extends Node {

	/**
	 * Each handler is stored within this list and their index is used to determine their priority.
	 * */
	private final List<String> handlers = new ArrayList<>();

	/**
     * Each running task is mapped to it's dependencies. This is done to check any conflicting tasks.
     * */
	private final Map<String, Task> runningTasks = new HashMap<>();

	public NewTaskManager() {
		super("Task Manager", "");
	}

	public void register(String handler) {
		handlers.add(handler);
	}

	public void register(Nameable handler) {
		register(handler.getName());
	}

	public void run(Task task) {
		// Only if the task can be run.
		if (can(task)) {
			// Store each running task which conflicts with our new task within this set.
			Set<Task> finishedTasks = new HashSet<>();
			for (String dependency : task.getDependencies()) {
				runningTasks.computeIfPresent(dependency, (key, value) -> {
					finishedTasks.add(value);
					return task;
				});
			}

			// Finish those tasks.
			for (Task finishedTask : finishedTasks) {
				finish(finishedTask);
			}
			task.onRun();
		}
	}

	/**
	 * @return True if this task can run.
	 * */
	public boolean can(Task task) {
		for (String dependency : task.getDependencies()) {
			// If a task with the same dependency is running and the index of the current task's handler is greater than the running task's handler, we cannot run.
			if (runningTasks.containsKey(dependency) && handlers.indexOf(task.getHandler()) >= handlers.indexOf(runningTasks.get(dependency).getHandler())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Will finish the given task only if it can be found running.
	 * */
	public void finish(Task task) {
		boolean wasRunning = false;
		for (String dependency : task.getDependencies()) {
			if (runningTasks.get(dependency) == task) {
				runningTasks.remove(dependency);
				wasRunning = true;
			}
		}
		// Ensure the function is only invoked once.
		if (wasRunning)
			task.onFinish();
	}

	@Override
	public boolean hasNode(JsonObject json) {
		return json.getAsJsonArray(getName()) != null;
	}

	@Override
	public void save(JsonObject json) throws IOException {
		super.save(json);
		JsonArray array = new JsonArray();
		for (String handler : handlers) {
			JsonObject handlerObject = new JsonObject();
			handlerObject.addProperty("name", handler);
			array.add(handlerObject);
		}
		json.add(getName(), array);
	}

	@Override
	public void load(JsonObject json) throws IOException {
		super.load(json);
		if (hasNode(json)) {
			handlers.clear();
			JsonArray objects = json.getAsJsonArray(getName());
			for (int i = 0; i < objects.size(); i++) {
				JsonObject itemObject = (JsonObject) objects.get(i);
				handlers.add(itemObject.get("name").getAsString());
			}
		}
	}

	/**
	 *
	 * */
	public interface Task {

		/**
		 * Invoked when this task is run.
		 * */
		void onRun();

		/**
		 * Invoked when this task is finished or replaced.
		 * */
		void onFinish();

		/**
		 * @return The handler for this task.
		 * */
		String getHandler();

		/**
		 * @return The dependencies which this task contains.
		 * */
		String[] getDependencies();
	}

	/**
	 * Basic implementation of the task interface.
	 * */
	public abstract class BasicTask implements Task {

		private final String handler;

		private String[] dependencies = new String[] {};

		public BasicTask(Nameable handler) {
			this(handler.getName());
		}

		public BasicTask(String handler) {
			this.handler = handler;
		}

		/**
		 * Adds the given strings to this tasks dependencies.
		 * */
		protected void addDependencies(String... dependencies) {
			List<String> list = Arrays.asList(this.dependencies);
			list.addAll(Arrays.asList(dependencies));
			this.dependencies = list.toArray(new String[list.size()]);
		}

		@Override
		public String getHandler() {
			return handler;
		}

		@Override
		public String[] getDependencies() {
			return dependencies;
		}
	}

	public class TaskDispatcher <T extends Task> {

	}
}
