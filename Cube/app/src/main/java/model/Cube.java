package model;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import opengles.AdrenoGLRenderer;

/**
 * Created by gmb on 08/02/17.
 */

public class Cube {
    private static Cube instance = null;

    final float[] cubePositionData =
        {
            // Face frontal
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,

            // Face direita
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,

            // Face traseira
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Face esquerda
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,

            // Face superior
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,

            // Face inferior
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f
        };

    private final String pointVertexShader =
            "uniform mat4 u_MVPMatrix;    \n"
            + "attribute vec4 a_Position; \n"
            + "void main()            \n"
            + "{            \n"
            + " gl_Position = u_MVPMatrix \n"
            + "            * a_Position;    \n"
            + " gl_PointSize = 5.0;            \n"
            + "}            \n";

    private final String pointFragmentShader =
            "precision mediump float;    \n"
            + "void main()            \n"
            + "{            \n"
            + " gl_FragColor = " +
            "vec4(0.0f, 0.55f, 0.0f, 1.0); \n"
            + "}            \n";

    private final int mBytesPerFloat = 4;
    private FloatBuffer mCubePositions;

    private int mPerVertexProgramHandle;



    public static Cube getInstance() {
        if (instance == null) {
            instance = new Cube ();
        }
        return instance;
    }

    private Cube(){
        mCubePositions = ByteBuffer.allocateDirect(cubePositionData.length * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubePositions.put(cubePositionData).position(0);

        int pointVertexShaderHandle =
                AdrenoGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, pointVertexShader);
        int pointFragmentShaderHandle =
                AdrenoGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                        pointFragmentShader);
        mPerVertexProgramHandle = createAndLinkProgram(pointVertexShaderHandle,
                pointFragmentShaderHandle, new String[]{"a_Position", "a_Color", "a_Normal"});
    }

    private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle,
                                     final String[] attributes) {
        int programHandle = GLES20.glCreateProgram();
        GLES20.glAttachShader(programHandle, vertexShaderHandle);
        GLES20.glAttachShader(programHandle, fragmentShaderHandle);
        if (attributes != null) {
            final int size = attributes.length;
            for (int i = 0; i < size; i++) {
                GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
            }
        }
        GLES20.glLinkProgram(programHandle);
        return programHandle;
    }


    public int getmPerVertexProgramHandle() {
        return mPerVertexProgramHandle;
    }

    public void setmPerVertexProgramHandle(int mPerVertexProgramHandle) {
        this.mPerVertexProgramHandle = mPerVertexProgramHandle;
    }

    public FloatBuffer getmCubePositions() {
        return mCubePositions;
    }
}
