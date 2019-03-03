package com.entpress.entpress.utility.localmessagemanager;

import android.support.annotation.NonNull;

public interface LocalMessageCallback {

    /**
     * This method is always executed from the UI thread.
     * @param localMessage event message
     */
    void handleMessage(@NonNull LocalMessage localMessage);
}
