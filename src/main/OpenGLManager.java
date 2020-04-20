package main;

import utils.FileUtils;
import org.lwjgl.opengl.*;

import static org.lwjgl.opengl.GL11.*;

public class OpenGLManager {
    public static int GPU_CALLS;

    public static void glBegin(int mode) {
        GL11.glBegin(mode);
        GPU_CALLS++;
    }

    /**
     * program shader, to which is attached a vertex and fragment shaders.
     * They are set to 0 as a check because GL will assign unique int
     * values to each
     */
    public static int programShader01 = 0;

    private static int loadShader(String shader) {
        int vertexShader;
        int fragmentShader;
        int programShader = ARBShaderObjects.glCreateProgramObjectARB();

        try {
            vertexShader = createShader("res/shaders/" + shader + ".vert", ARBVertexShader.GL_VERTEX_SHADER_ARB);
            fragmentShader = createShader("res/shaders/" + shader + ".frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        if (vertexShader == 0 || fragmentShader == 0 || programShader == 0) return 0;

        /**
         * If the vertex and fragment shaders setup successfully,
         * attach them to the shader program, link the shader program,
         * and validate.
         */
        ARBShaderObjects.glAttachObjectARB(programShader, vertexShader);
        ARBShaderObjects.glAttachObjectARB(programShader, fragmentShader);

        ARBShaderObjects.glLinkProgramARB(programShader);
        if (ARBShaderObjects.glGetObjectParameteriARB(programShader, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(programShader));
            return 0;
        }

        ARBShaderObjects.glValidateProgramARB(programShader);
        if (ARBShaderObjects.glGetObjectParameteriARB(programShader, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            System.err.println(getLogInfo(programShader));
            return 0;
        }

        return programShader;
    }

    public static void prepareOpenGL() {
        GL.createCapabilities();

        //Load shaders
        programShader01 = loadShader("shader01");
    }

    public static void prepareFrame() {
        GPU_CALLS = 0;
        glClear(GL_COLOR_BUFFER_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Parameters.getResolutionWidth(), Parameters.getResolutionHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glEnable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void drawTexture(int x, int y, double u, double v, double u2, double v2, float spriteWidth, float spriteHeight) {
        drawTexture(x, y, u, v, u2, v2, spriteWidth, spriteHeight, 1f, 1f, 1f, 1f);
    }

    public static void drawTexture(int x, int y, double u, double v, double u2, double v2, float spriteWidth, float spriteHeight, float r, float g, float b) {
        drawTexture(x, y, u, v, u2, v2, spriteWidth, spriteHeight, 1f, r, g, b);
    }

    public static void drawTexture(int x, int y, double u, double v, double u2, double v2, float spriteWidth, float spriteHeight, double transparency, float r, float g, float b) {
        glColor4f(r, g, b, (float) transparency);
        glTexCoord2d(u, v);
        glVertex2d(x, y);
        glTexCoord2d(u, v2);
        glVertex2d(x, y + spriteHeight);
        glTexCoord2d(u2, v2);
        glVertex2d(x + spriteWidth, y + spriteHeight);
        glTexCoord2d(u2, v);
        glVertex2d(x + spriteWidth, y);
    }

    public static void drawRectangle(int x, int y, float width, float height) {
        drawRectangle(x, y, width, height, 1.0, 1f, 1f, 1f);
    }

    public static void drawRectangle(int x, int y, float width, float height, float greyValue) {
        drawRectangle(x, y, width, height, 1.0, greyValue, greyValue, greyValue);
    }

    public static void drawRectangle(int x, int y, float width, float height, double transparency, float greyValue) {
        drawRectangle(x, y, width, height, transparency, greyValue, greyValue, greyValue);
    }

    public static void drawRectangle(int x, int y, float width, float height, double transparency, float r, float g, float b) {
        glColor4f(r, g, b, (float) transparency);
        glVertex2f(x, y);
        glVertex2f(x, y + height);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y);
        glVertex2f(x, y + height);
        glVertex2f(x + width, y + height);
    }

    public static void drawTriangle(Coordinates vertex1, Coordinates vertex2, Coordinates vertex3, double transparency, float greyValue) {
        drawTriangle(
                new int[]{(int) vertex1.x, (int) vertex1.y},
                new int[]{(int) vertex2.x, (int) vertex2.y},
                new int[]{(int) vertex3.x, (int) vertex3.y},
                transparency, greyValue, greyValue, greyValue);
    }

    public static void drawTriangle(int[] vertex1, int[] vertex2, int[] vertex3, double transparency, float greyValue) {
        drawTriangle(vertex1, vertex2, vertex3, transparency, greyValue, greyValue, greyValue);
    }

    public static void drawTriangle(int[] vertex1, int[] vertex2, int[] vertex3, double transparency, float r, float g, float b) {
        glColor4f(r, g, b, (float) transparency);
        glVertex2f(vertex1[0], vertex1[1]);
        glVertex2f(vertex2[0], vertex2[1]);
        glVertex2f(vertex3[0], vertex3[1]);
    }

    /*
     * With the exception of syntax, setting up vertex and fragment shaders
     * is the same.
     * @param the name and path to the vertex shader
     */
    private static int createShader(String filename, int shaderType) throws Exception {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if (shader == 0) return 0;

            ARBShaderObjects.glShaderSourceARB(shader, FileUtils.readFileAsString(filename));
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE) {
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
            }

            return shader;
        } catch (Exception e) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw e;
        }
    }

    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
}
