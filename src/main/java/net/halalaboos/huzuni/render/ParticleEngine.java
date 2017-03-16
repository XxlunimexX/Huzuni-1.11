/**
 *
 */
package net.halalaboos.huzuni.render;

import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.api.util.gl.Texture;
import net.halalaboos.mcwrapper.api.util.math.MathUtils;
import org.lwjgl.opengl.GL11;

import java.util.*;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author Halalaboos
 * @since Jul 21, 2013
 */
public class ParticleEngine {
	
    private static final Texture texture = new Texture("particles.png");
   
    private final Random random = new Random();
    
    private final List<Particle> particles = new ArrayList<>();
    
    private final boolean randomDespawn;

    public ParticleEngine() {
        this(false);
    }

    public ParticleEngine(boolean randomDespawn) {
        this.randomDespawn = randomDespawn;
    }

    public void render() {
        getGLStateManager().enableBlend();
		getGLStateManager().blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		getGLStateManager().enableTexture2D();
        for (Particle particle : particles) {
            particle.applyPhysics();
			getGLStateManager().pushMatrix();
            texture.bindTexture();
            float scale = ((particle.life) / (getMaxLife())) / 5;
            glScalef(scale, scale, scale);
            GLUtils.glColor(1F, 1F, 1F, ((particle.life) / (getMaxLife())) / 5);
            renderTexture(320, 32, particle.x * (1F / scale), particle.y * (1F / scale), 32, 32, 320 - (MathUtils.ceil(particle.life / (getMaxLife() / 10F)) * 32), 0, 32, 32);
			getGLStateManager().popMatrix();
        }
    }
    
    private void renderTexture(int textureWidth, int textureHeight, float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
        float renderSRCX = srcX / textureWidth,
                renderSRCY = srcY / textureHeight,
                renderSRCWidth = (srcWidth) / textureWidth,
                renderSRCHeight = (srcHeight) / textureHeight;
        glBegin(GL_TRIANGLES);
        glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        glVertex2d(x + width, y);
        glTexCoord2f(renderSRCX, renderSRCY);
        glVertex2d(x, y);
        glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        glVertex2d(x, y + height);
        glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        glVertex2d(x, y + height);
        glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        glVertex2d(x + width, y + height);
        glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        glVertex2d(x + width, y);
        glEnd();
    }

    public void updateParticles() {
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.update();
            if (particle.life <= 0)
                particles.remove(particle);
        }
    }

    public void spawnParticles(int spawnX, int spawnY, int dispurseX, int dispurseY, float velocity, float gravity) {
        int startX = spawnX + random.nextInt(dispurseX),
                startY = spawnY + random.nextInt(dispurseY);
        Particle particle = new Particle(startX, startY, velocity, gravity);
        particles.add(particle);
    }

    private class Particle {
        float life, x, y,
                motionX, motionY, gravity;

        private Particle(float x, float y, float velocity, float gravity) {
            this.x = x;
            this.y = y;
            this.motionX = random.nextFloat() * velocity;
            this.motionY = random.nextFloat() * velocity;
            if (random.nextBoolean()) motionX = -motionX;
            if (random.nextBoolean()) motionY = -motionY;
            this.life = getMaxLife();
            this.gravity = gravity;
        }

        private void applyPhysics() {
            x += motionX * 0.1F;
            y += motionY * 0.1F;
            motionX *= 0.99F;
            motionY *= 0.99F;
            y += gravity * 0.1F;
        }

        private void update() {
            if (randomDespawn) {
                if (random.nextBoolean())
                    life -= random.nextFloat() * 2;
            } else
                life -= random.nextFloat() * 2;
        }
    }
    
    private int getMaxLife() {
    	return 50;
    }

}
