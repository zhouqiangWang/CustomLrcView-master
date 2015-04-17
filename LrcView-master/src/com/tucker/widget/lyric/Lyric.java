package com.tucker.widget.lyric;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/9.
 */
public class Lyric {

    private final String TAG = "MusicLyric";

    private Context mContext;

    private int mDuration;

    private String mSongPath;

    private List<LrcRow> rows;

    public Lyric(int duration, String songPath) {
        this.mDuration = duration;
        this.mSongPath = songPath;

        loadLyric();
    }

    private void loadLyric() {
        String lrcPath = mSongPath.substring(0, mSongPath.lastIndexOf(".")) + "lrc";
        Log.d(TAG + "-wang", "lrcPath = " + lrcPath);

        // lrc format error, reference : http://blog.csdn.net/yangchuxi/article/details/6670538
        // InputStreamReader default constructor will use System.getProperty("file.encoding", "UTF-8"); for encoding
        try {
            FileInputStream lrcFile = mContext.openFileInput(lrcPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(lrcFile));
            String lrcLine;

            while ((lrcLine = bufferedReader.readLine()) != null) {
                CreateLrcRow(lrcLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将歌词文件中的某一行 解析成一个List<LrcRow>
     * 因为一行中可能包含了多个LrcRow对象.
     * reference : http://baike.baidu.com/subview/80650/8795771.htm
     *
     * @return
     */
    private List<LrcRow> CreateLrcRow(String lrcLine) {
        if (!lrcLine.startsWith("[") || lrcLine.indexOf("]") != 9) {
            return null;
        }
        //最后一个"]"
        int lastIndexOfRightBracket = lrcLine.lastIndexOf("]");
        //歌词内容
        String content = lrcLine.substring(lastIndexOfRightBracket + 1, lrcLine.length());
        //截取出歌词时间，并将"[" 和"]" 替换为"-"   [offset:0]
        // -03:33.02--00:36.37-
        String times = lrcLine.substring(0, lastIndexOfRightBracket + 1).replace("[", "-").replace("]", "-");
        String[] timesArray = times.split("-");
        List<LrcRow> lrcRows = new ArrayList<LrcRow>();
        for (String time : timesArray) {
            if (TextUtils.isEmpty(time.trim())) {
                continue;
            }
            //
            try {
                LrcRow lrcRow = new LrcRow(formatTime(time), content);
                lrcRows.add(lrcRow);
            } catch (Exception e) {
                Log.w("LrcRow", e.getMessage());
            }
        }
        return lrcRows;
    }

    private int formatTime(String timeStr) {
        timeStr = timeStr.replace('.', ':');
        String[] times = timeStr.split(":");

        return Integer.parseInt(times[0])*60*1000
                + Integer.parseInt(times[1])*1000
                + Integer.parseInt(times[2]);
    }

    public class LrcRow implements Comparable<LrcRow> {

        String content;

        int beginTime;

        int totalTime;

        public LrcRow(int beginTime, String content) {
            this.beginTime = beginTime;
            this.content = content;
        }

        @Override
        public int compareTo(LrcRow another) {
            return (this.beginTime - another.beginTime);
        }
    }

    public String getLrcByTime(int time) {

        return null;
    }

    public String getLrcByIndex(int index) {

        return null;
    }
}
