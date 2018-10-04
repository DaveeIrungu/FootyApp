package com.example.davee.footyappv2;

import android.view.View;

public interface ItemClickListener {
    // will be used to implement RecyclerView to load news feed through a web browser on the android device
    void onClick(View view, int position, boolean isLongClick);
}