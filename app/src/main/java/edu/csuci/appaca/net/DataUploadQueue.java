package edu.csuci.appaca.net;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import edu.csuci.appaca.utils.TimeUtils;

public class DataUploadQueue {

    private enum ChunkQueue {
        INSTANCE;
        BlockingQueue<DataChunkUploader> queue;

        ChunkQueue() {
            queue = new LinkedBlockingDeque<>();
        }
    }

    private static boolean isProcessing = false;
    private static long lastUploadTime = 0;
    private static DataChunkUploader mostRecentUploaded = null;

    public static boolean isProcessing() {
        return isProcessing;
    }

    public static void addUpload(Activity parent) throws JSONException {
        long currentTime = TimeUtils.getCurrentTime();
        if(currentTime - lastUploadTime < 5) {
            return;
        }
        lastUploadTime = currentTime;
        try {
            final int size = ChunkQueue.INSTANCE.queue.size();
            ChunkQueue.INSTANCE.queue.put(DataChunkUploader.newUploader(parent));
            if(size == 0 && !isProcessing) {
                process(); // start processing if queue was recently empty and not currently busy
            }
        } catch (InterruptedException e) {
            Log.e(DataUploadQueue.class.getName(), e.getMessage(), e);
        }
    }

    public static void process() {
        if (ChunkQueue.INSTANCE.queue.size() == 0) {
            if(mostRecentUploaded != null && mostRecentUploaded.isDone()) {
                isProcessing = false;
                mostRecentUploaded = null;
            }
            return;
        }
        isProcessing = true;
        try {
            mostRecentUploaded = ChunkQueue.INSTANCE.queue.take();
            mostRecentUploaded.process();
        } catch (InterruptedException e) {
            Log.e(DataUploadQueue.class.getName(), e.getMessage(), e);
        }
    }

}
