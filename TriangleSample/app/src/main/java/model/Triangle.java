package model;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import opengles.AdrenoGLRenderer;

/**
 * Created by gmb on 07/02/17.
 */

public class Triangle {
    private FloatBuffer vertexBuffer;

    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    static final int COORDS_PER_VERTEX = 3;

    static float triangleCoords[] = { // no sentido anti-horário:
            0.0f, 0.622008459f, 0.0f, // topo
            -0.5f, -0.311004243f, 0.0f, // lado inferior esquerdo
            //0.0f, -0.622008459f, 0.0f, // inferior
            0.5f, -0.311004243f, 0.0f // lado inferior direiro
    };

    /*private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
            "void main() {" +
            " gl_Position = vPosition;" +
            "}";*/
    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            " gl_FragColor = vColor;" +
            "}";

    private int mProgram;

    private int mPositionHandle;
    private int mColorHandle;
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    " gl_Position = uMVPMatrix * vPosition;" +
                    "}";
    private int mMVPMatrixHandle;

    public Triangle() {
        // Declarando um objeto do tipo ByteBuffer como  tamanho
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        // Ordenando o buffer
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        int vertexShader = AdrenoGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = AdrenoGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mMVPMatrix) {
        GLES20.glUseProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, vertexStride, vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
