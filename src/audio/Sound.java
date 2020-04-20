package audio;

public class Sound {
    private int buffer;
    private int index;
    private SoundType soundType;

    public enum SoundType {
        EFFECT, MUSIC, AMBIENCE
    }

    public Sound(int buffer, int index, SoundType soundType) {
        this.buffer = buffer;
        this.index = index;
        this.soundType = soundType;
    }

    public int getBuffer() {
        return buffer;
    }

    public int getIndex() {
        return index;
    }

    public SoundType getType() {
        return soundType;
    }
}
