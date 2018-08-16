package atpl.com.texturevideoview;

abstract class VLCEvent {
    public final int type;
    protected final long arg1;
    protected final float arg2;

    protected VLCEvent(int type) {
        this.type = type;
        this.arg1 = 0;
        this.arg2 = 0;
    }
    protected VLCEvent(int type, long arg1) {
        this.type = type;
        this.arg1 = arg1;
        this.arg2 = 0;
    }
    protected VLCEvent(int type, float arg2) {
        this.type = type;
        this.arg1 = 0;
        this.arg2 = arg2;
    }

    /**
     * Listener for libvlc events
     *
     * @see VLCEvent
     */
    public interface Listener<T extends VLCEvent> {
        void onEvent(T event);
    }
}