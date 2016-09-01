package com.melody.education.data;

import com.devbrackets.android.playlistcore.manager.IPlaylistItem;
import com.melody.education.manager.PlaylistManager;
import com.melody.education.model.Conversation;

/**
 * A custom {@link IPlaylistItem}
 * to hold the information pertaining to the audio and video items
 */
public class MediaItem implements IPlaylistItem {

    private Conversation sample;
    boolean isAudio;

    public MediaItem(Conversation sample, boolean isAudio) {
        this.sample = sample;
        this.isAudio = isAudio;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public long getPlaylistId() {
        return 0;
    }

    @Override
    public int getMediaType() {
        return isAudio ? PlaylistManager.AUDIO : PlaylistManager.VIDEO;
    }

    @Override
    public String getMediaUrl() {
        return sample.Audio;
    }

    @Override
    public String getDownloadedMediaUri() {
        return null;
    }

    @Override
    public String getThumbnailUrl() {
        return sample.Picture;
    }

    @Override
    public String getArtworkUrl() {
        return sample.Picture;
    }

    @Override
    public String getTitle() {
        return sample.Title;
    }

    @Override
    public String getAlbum() {
        return sample.Nhat;
    }

    @Override
    public String getArtist() {
        return sample.Description;
    }
}