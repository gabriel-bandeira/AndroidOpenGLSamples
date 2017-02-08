package opengles;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import model.Triangle;

/**
 * Created by gmb on 07/02/17.
 */

public class AdrenoGLRenderer implements GLSurfaceView.Renderer{
    private Triangle triangulo;

    private final float[] mProjectionMatrix = new float[16];

    private final float[] mMVPMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    // Matriz de rotação
    private float[] mRotationMatrix = new float[16];

    public volatile float mAngle;
    public float getAngle() {
        return mAngle;
    }
    public void setAngle(float angle) {
        mAngle = angle;
    }

    public AdrenoGLRenderer() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //Configura cor de fundo
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // inicialização da classe
        triangulo = new Triangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        float[] scratch = new float[16];
        // Criando a rotação para o triângulo
        //long time = SystemClock.uptimeMillis() % 4000L;
        //float angle = 0.090f * ((int) time);
        //Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);

        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //triangulo.draw(mMVPMatrix);

        // Combine as matrizes (rotação, projeção e câmera)
        // O mMVPMatrix precisa ser enviado antes do objeto de rotação para o produto
        // da matriz de multiplicação ser o correto
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        triangulo.draw(scratch);
    }

    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
