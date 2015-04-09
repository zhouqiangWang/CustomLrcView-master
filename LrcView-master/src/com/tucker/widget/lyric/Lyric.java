package com.tucker.widget.lyric;

import java.util.List;

/**
 * Created by Administrator on 2015/4/9.
 */
public class Lyric {

    private int mDuration;

    private String mSongPath;

    private List<LrcRow> rows;

    public Lyric(int duration, String songPath) {
        this.mDuration = duration;
        this.mSongPath = songPath;
    }

    public class LrcRow implements Comparable<LrcRow>{

        String content;

        int beginTime;

        int totalTime;

        public LrcRow(){

        }

        @Override
        public int compareTo(LrcRow another) {
            return (this.beginTime - another.beginTime);
        }
    }

    public String getLrcByTime(int time){

        return null;
    }

    public String getLrcByIndex(int index){

        return null;
    }
}
