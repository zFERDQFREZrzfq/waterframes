package me.srrapero720.waterframes.common.block.data;

import me.srrapero720.waterframes.WFConfig;
import me.srrapero720.waterframes.common.block.data.types.AudioPosition;
import me.srrapero720.waterframes.common.block.data.types.PositionHorizontal;
import me.srrapero720.waterframes.common.block.data.types.PositionVertical;
import me.srrapero720.waterframes.common.block.entity.DisplayTile;
import me.srrapero720.waterframes.common.screens.DisplayScreen;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import team.creative.creativecore.common.util.math.vec.Vec2f;

import java.util.UUID;

public class DisplayData {
    public static final String URL = "url";
    public static final String PLAYER_UUID = "player_uuid";
    public static final String ACTIVE = "active";
    public static final String MIN_X = "min_x";
    public static final String MIN_Y = "min_y";
    public static final String MAX_X = "max_x";
    public static final String MAX_Y = "max_y";

    public static final String FLIP_X = "flip_x";
    public static final String FLIP_Y = "flip_y";

    public static final String ROTATION = "rotation";
    public static final String ALPHA = "alpha";
    public static final String BRIGHTNESS = "brightness";
    public static final String RENDER_DISTANCE = "render_distance";

    public static final String VOLUME = "volume";
    public static final String VOL_RANGE_MIN= "volume_min_range";
    public static final String VOL_RANGE_MAX = "volume_max_range";
    public static final String LOOP = "loop";
    public static final String PAUSED = "paused";
    public static final String MUTED = "muted";
    public static final String TICK = "tick";
    public static final String TICK_MAX = "tick_max";
    public static final String DATA_V = "data_v";

    // FRAME KEYS
    public static final String VISIBLE_FRAME = "frame_visibility";
    public static final String RENDER_BOTH_SIDES = "render_both";

    // PROJECTOR
    public static final String PROJECTION_DISTANCE = "projection_distance";
    public static final String AUDIO_OFFSET = "audio_offset";

    public static final short V = 2;

    public String url = "";
    public UUID uuid = Util.NIL_UUID;
    public boolean active = true;
    public final Vec2f min = new Vec2f(0f, 0f); // TODO: use vanilla Vec2
    public final Vec2f max = new Vec2f(1f, 1f);

    public boolean flipX = false;
    public boolean flipY = false;

    public float rotation = 0;
    public int alpha = 255;
    public int brightness = 255;
    public int renderDistance = WFConfig.maxRenDis(32);

    public int volume = WFConfig.maxVol();
    public int maxVolumeDistance = WFConfig.maxVolDis(20);
    public int minVolumeDistance = Math.min(5, maxVolumeDistance);

    public boolean loop = true;
    public boolean paused = false;
    public boolean muted = false;
    public long tick = 0;
    public long tickMax = -1;

    // FRAME VALUES
    public boolean frameVisibility = true;
    public boolean renderBothSides = false;

    // PROJECTOR VALUES
    public float projectionDistance = WFConfig.maxProjDis(8f);
    public float audioOffset = 0;

    public PositionHorizontal getPosX() { return this.min.x == 0 ? PositionHorizontal.LEFT : this.max.x == 1 ? PositionHorizontal.RIGHT : PositionHorizontal.CENTER; }
    public PositionVertical getPosY() { return this.min.y == 0 ? PositionVertical.TOP : this.max.y == 1 ? PositionVertical.BOTTOM : PositionVertical.CENTER; }
    public float getWidth() { return this.max.x - this.min.x; }
    public float getHeight() { return this.max.y - this.min.y; }

    public void save(CompoundTag nbt, DisplayTile displayTile) {
        nbt.putString(URL, url);
        nbt.putUUID(PLAYER_UUID, uuid);
        nbt.putBoolean(ACTIVE, active);
        if (displayTile.canResize()) {
            nbt.putFloat(MIN_X, min.x);
            nbt.putFloat(MIN_Y, min.y);
            nbt.putFloat(MAX_X, max.x);
            nbt.putFloat(MAX_Y, max.y);
            nbt.putFloat(ROTATION, rotation);
        }
        nbt.putInt(RENDER_DISTANCE, renderDistance);
        nbt.putBoolean(FLIP_X, flipX);
        nbt.putBoolean(FLIP_Y, flipY);
        nbt.putInt(ALPHA, alpha);
        nbt.putInt(BRIGHTNESS, brightness);
        nbt.putInt(VOLUME, volume);
        nbt.putInt(VOL_RANGE_MIN, minVolumeDistance);
        nbt.putInt(VOL_RANGE_MAX, maxVolumeDistance);
        nbt.putBoolean(PAUSED, paused);
        nbt.putBoolean(MUTED, muted);
        nbt.putLong(TICK, tick);
        nbt.putLong(TICK_MAX, tickMax);
        nbt.putBoolean(LOOP, loop);

        if (displayTile.canRenderBackside()) {
            nbt.putBoolean(RENDER_BOTH_SIDES, renderBothSides);
        }

        if (displayTile.canHideModel()) {
            nbt.putBoolean(VISIBLE_FRAME, frameVisibility);
        }

        if (displayTile.canProject()) {
            nbt.putFloat(PROJECTION_DISTANCE, projectionDistance);
            nbt.putFloat(AUDIO_OFFSET, audioOffset);
        }

        nbt.putShort(DATA_V, V);
    }

    public void load(CompoundTag nbt, DisplayTile displayTile) {
        this.url = nbt.getString(URL);
        this.uuid = nbt.contains(PLAYER_UUID) ? nbt.getUUID(PLAYER_UUID) : this.uuid;
        this.active = nbt.contains(ACTIVE) ? nbt.getBoolean(ACTIVE) : this.active;
        if (displayTile.canResize()) {
            this.min.x = nbt.getFloat(MIN_X);
            this.min.y = nbt.getFloat(MIN_Y);
            this.max.x = nbt.getFloat(MAX_X);
            this.max.y = nbt.getFloat(MAX_Y);
            this.rotation = nbt.getFloat(ROTATION);
        }
        this.renderDistance = WFConfig.maxRenDis(nbt.getInt(RENDER_DISTANCE));
        this.flipX = nbt.getBoolean(FLIP_X);
        this.flipY = nbt.getBoolean(FLIP_Y);
        this.alpha = nbt.contains(ALPHA) ? nbt.getInt(ALPHA) : this.alpha;
        this.brightness = nbt.contains(BRIGHTNESS) ? nbt.getInt(BRIGHTNESS) : this.alpha;
        this.volume = nbt.contains(VOLUME) ? WFConfig.maxVol(nbt.getInt(VOLUME)) : this.volume;
        this.maxVolumeDistance = nbt.contains(VOL_RANGE_MAX) ? WFConfig.maxVolDis(nbt.getInt(VOL_RANGE_MAX)) : this.maxVolumeDistance;
        this.minVolumeDistance = nbt.contains(VOL_RANGE_MIN) ? Math.min(nbt.getInt(VOL_RANGE_MIN), this.maxVolumeDistance) : this.minVolumeDistance;
        this.paused = nbt.getBoolean(PAUSED);
        this.muted = nbt.getBoolean(MUTED);
        this.tick = nbt.getLong(TICK);
        this.tickMax = nbt.contains(TICK_MAX) ? nbt.getLong(TICK_MAX) : this.tickMax;
        this.loop = nbt.getBoolean(LOOP);

        if (displayTile.canHideModel()) {
            this.frameVisibility = nbt.getBoolean(VISIBLE_FRAME);
        }

        if (displayTile.canRenderBackside()) {
            this.renderBothSides = nbt.getBoolean(RENDER_BOTH_SIDES);
        }

        if (displayTile.canProject()) {
            this.projectionDistance = nbt.contains(PROJECTION_DISTANCE) ? WFConfig.maxProjDis(nbt.getInt(PROJECTION_DISTANCE)) : this.projectionDistance;
            this.audioOffset = nbt.contains(AUDIO_OFFSET) ? nbt.getFloat(AUDIO_OFFSET) : this.audioOffset;
        }

        switch (nbt.getShort(DATA_V)) {
            case 1 -> {
                this.alpha = (int) (nbt.getFloat(ALPHA) * 255);
                this.brightness = (int) (nbt.getFloat(BRIGHTNESS) * 255);
            }

            default -> { // NO EXISTS
                if (!nbt.contains("maxx")) return; // no exists then ignore, prevents broke new data on 2.0
                this.min.x = nbt.getFloat("minx");
                this.min.y = nbt.getFloat("miny");
                this.max.x = nbt.getFloat("maxx");
                this.max.y = nbt.getFloat("maxy");

                this.flipX = nbt.getBoolean("flipX");
                this.flipY = nbt.getBoolean("flipY");

                this.maxVolumeDistance = WFConfig.maxVolDis((int) nbt.getFloat("max"));
                this.minVolumeDistance = Math.min((int) nbt.getFloat("min"), maxVolumeDistance);

                this.renderDistance = nbt.getInt("render");

                if (displayTile.canHideModel()) {
                    this.frameVisibility = nbt.getBoolean("visibleFrame");
                }

                if (displayTile.canRenderBackside()) {
                    this.renderBothSides = nbt.getBoolean("bothSides");
                }
            }
        }

        this.restrictWidth();
        this.restrictHeight();
    }

    public void setAudioPosition(AudioPosition position) {
        this.audioOffset = switch (position) {
            case BLOCK -> 0f;
            case PROJECTION -> projectionDistance;
            case CENTER -> projectionDistance / 2f;
        };
    }
    public AudioPosition getAudioPosition() {
        return audioOffset == 0 ? AudioPosition.BLOCK : audioOffset == projectionDistance ? AudioPosition.PROJECTION : AudioPosition.CENTER;
    }

    public void setWidth(final float width) { this.setWidth(this.getPosX(), width); }
    public void setWidth(final PositionHorizontal position, final float width) {
        switch (position) {
            case LEFT -> {
                this.min.x = 0;
                this.max.x = width;
            }
            case RIGHT -> {
                this.min.x = 1 - width;
                this.max.x = 1;
            }
            default -> {
                float middle = width / 2;
                this.min.x = 0.5F - middle;
                this.max.x = 0.5F + middle;
            }
        }
    }

    public void setHeight(final float height) { this.setHeight(this.getPosY(), height); }
    public void setHeight(final PositionVertical position, final float height) {
        switch (position) {
            case TOP -> {
                this.min.y = 0;
                this.max.y = height;
            }
            case BOTTOM -> {
                this.min.y = 1 - height;
                this.max.y = 1;
            }
            default -> {
                float middle = height / 2;
                this.min.y = 0.5F - middle;
                this.max.y = 0.5F + middle;
            }
        }
    }

    private void restrictWidth() {
        float maxWidth = WFConfig.maxWidth();
        if (getWidth() > maxWidth) {
            switch (getPosX()) {
                case LEFT -> {
                    this.min.x = 0;
                    this.max.x = maxWidth;
                }
                case RIGHT -> {
                    this.min.x = 1 - maxWidth;
                    this.max.x = 1;
                }
                default -> {
                    float middle = maxWidth / 2f;
                    this.min.x = 0.5F - middle;
                    this.max.x = 0.5F + middle;
                }
            }
        }
    }

    private void restrictHeight() {
        float maxHeight = WFConfig.maxHeight();
        if (getHeight() > maxHeight) {
            switch (getPosY()) {
                case TOP -> {
                    this.min.y = 0f;
                    this.max.y = maxHeight;
                }
                case BOTTOM -> {
                    this.min.y = 1f - maxHeight;
                    this.max.y = 1f;
                }
                default -> {
                    float middle = maxHeight / 2f;
                    this.min.y = 0.5F - middle;
                    this.max.y = 0.5F + middle;
                }
            }
        }
    }

    public static CompoundTag build(DisplayScreen screen, DisplayTile tile) {
        CompoundTag nbt = new CompoundTag();

        nbt.putString(URL, screen.url.getText());
        nbt.putBoolean(ACTIVE, true); // reset

        if (tile.canResize()) {
            nbt.putFloat("width", Math.max(0.1F, (float) screen.widthField.getValue()));
            nbt.putFloat("height", Math.max(0.1F, (float) screen.heightField.getValue()));
            nbt.putInt("pos_x",  screen.pos_view.getX().ordinal());
            nbt.putInt("pos_y", screen.pos_view.getY().ordinal());
            nbt.putFloat(ROTATION, (float) screen.rotation.getValue());
        }

        nbt.putBoolean(FLIP_X, screen.flip_x.value);
        nbt.putBoolean(FLIP_Y, screen.flip_y.value);

        nbt.putInt(ALPHA, screen.alpha.getIntValue());
        nbt.putInt(BRIGHTNESS, screen.brightness.getIntValue());
        nbt.putInt(RENDER_DISTANCE, screen.render_distance.getIntValue());

        nbt.putInt(VOLUME, screen.volume.getIntValue());
        nbt.putInt(VOL_RANGE_MIN, screen.volume_min.getIntValue());
        nbt.putInt(VOL_RANGE_MAX, screen.volume_max.getIntValue());

        if (tile.canHideModel()) {
            nbt.putBoolean(VISIBLE_FRAME, screen.show_model.value);
        }

        if (tile.canRenderBackside()) {
            nbt.putBoolean(RENDER_BOTH_SIDES, screen.render_behind.value);
        }

        if (tile.canProject()) {
            nbt.putFloat(PROJECTION_DISTANCE, (float) screen.projection_distance.getValue());
            nbt.putInt(AUDIO_OFFSET, screen.audioOffset.getState());
        }

        return nbt;
    }

    public static void sync(DisplayTile block, Player player, CompoundTag nbt) {
        String url = nbt.getString(URL);
        if (WFConfig.canSave(player, url)) {
            if (!block.data.url.equals(url)) {
                block.data.tick = 0;
                block.data.tickMax = -1;
            }
            block.data.url = url;
            block.data.uuid = !block.data.url.isEmpty() ? player.getUUID() : Util.NIL_UUID;
            block.data.active = nbt.getBoolean(ACTIVE);

            if (block.canResize()) {
                float width = WFConfig.maxWidth(nbt.getFloat("width"));
                float height = WFConfig.maxHeight(nbt.getFloat("height"));
                int posX = nbt.getInt("pos_x");
                int posY = nbt.getInt("pos_y");

                block.data.setWidth(PositionHorizontal.VALUES[posX], width);
                block.data.setHeight(PositionVertical.VALUES[posY], height);
                block.data.rotation = nbt.getFloat(ROTATION);
            }

            block.data.flipX = nbt.getBoolean(FLIP_X);
            block.data.flipY = nbt.getBoolean(FLIP_Y);
            block.data.alpha = nbt.getInt(ALPHA);
            block.data.brightness = nbt.getInt(BRIGHTNESS);
            block.data.renderDistance = WFConfig.maxRenDis(nbt.getInt(RENDER_DISTANCE));
            block.data.volume = WFConfig.maxVol(nbt.getInt(VOLUME));
            block.data.maxVolumeDistance = WFConfig.maxVolDis(nbt.getInt(VOL_RANGE_MAX));
            block.data.minVolumeDistance = Math.min(nbt.getInt(VOL_RANGE_MIN), block.data.maxVolumeDistance);
            if (block.data.minVolumeDistance > block.data.maxVolumeDistance)
                block.data.maxVolumeDistance = block.data.minVolumeDistance;

            if (block.canHideModel()) {
                block.data.frameVisibility = nbt.getBoolean(VISIBLE_FRAME);
            }

            if (block.canRenderBackside()) {
                block.data.renderBothSides = nbt.getBoolean(RENDER_BOTH_SIDES);
            }

            if (block.canProject()) {
                int mode = nbt.getInt(AUDIO_OFFSET);

                block.data.projectionDistance = WFConfig.maxProjDis(nbt.getFloat(PROJECTION_DISTANCE));
                block.data.setAudioPosition(AudioPosition.VALUES[mode]);
            }
        }

        block.setDirty();
    }
}