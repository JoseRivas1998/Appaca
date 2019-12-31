package edu.csuci.appaca.net;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DataUploadQueue {

    private enum ChunkQueue {
        INSTANCE;
        BlockingQueue<DataChunkUploader> queue;

        ChunkQueue() {
            queue = new LinkedBlockingDeque<>();
        }
    }

    public static void addUpload(Activity parent) throws JSONException {
        try {
            final int size = ChunkQueue.INSTANCE.queue.size();
            ChunkQueue.INSTANCE.queue.put(DataChunkUploader.newUploader(parent));
            if(size == 0) {
                process(); // start processing if queue was recently empty
            }
        } catch (InterruptedException e) {
            Log.e(DataUploadQueue.class.getName(), e.getMessage(), e);
        }
    }

    public static void process() {
        if (ChunkQueue.INSTANCE.queue.size() == 0) {
            return;
        }
        try {
            ChunkQueue.INSTANCE.queue.take().process();
        } catch (InterruptedException e) {
            Log.e(DataUploadQueue.class.getName(), e.getMessage(), e);
        }
    }

}
