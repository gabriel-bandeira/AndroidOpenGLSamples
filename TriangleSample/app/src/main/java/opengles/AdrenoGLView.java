package opengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by gmb on 07/02/17.
 */

public class AdrenoGLView extends GLSurfaceView {
    // Cria um objeto do tipo do controlador
    private AdrenoGLRenderer renderer;

    private final float TOUCH_SCALE_FACTOR = 180.0f / (320 * 5.3f);
    private float mPreviousX;
    private float mPreviousY;

    public AdrenoGLView(Context context) {
        super(context);

        // Cria um OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        // Cria uma instância do Renderer
        renderer = new AdrenoGLRenderer();

        // Configura o Renderer para desenhar na surface view criada - AdrenoGLView
        setRenderer(renderer);

        // Render a view apenas quando há alteração nos dados
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = mPreviousX - x;
                float dy = mPreviousY - y;
                // Altera a rotação para após a linha central
                if (y < getHeight() / 2) {
                    dx = dx * -1 ;
                }
                // Altera a rotação para a esquerda
                if (x > getWidth() / 2) {
                    dy = dy * -1 ;
                }
                renderer.setAngle(renderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
                break;

            default:
                break;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
