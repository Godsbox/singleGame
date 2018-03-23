/*
 * Copyright (c) 2017-present 3000.com All Rights Reserved.
 */
package com.lzj.arch.widget.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.lzj.arch.R;

import static android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
import static java.lang.Integer.MAX_VALUE;

/**
 * 可以展开更多的文本视图。
 *
 * @author 吴吉林
 */
public class ExpandableTextView extends AppCompatTextView {
    private int maxLines;
    private BufferType bufferType = BufferType.NORMAL;
    private String text;
    private String moreText;
    private String lessText;
    private int moreColor;
    private int lessColor;
    private OnExpandChangeListener onExpandChangeListener;

    private ViewTreeObserver.OnGlobalLayoutListener listener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
            Layout layout = getLayout();
            int lineCount = layout.getLineCount();
            if (lineCount > maxLines) {
                lineCount = maxLines;
            }
            if (layout.getEllipsisCount(lineCount - 1) <= 0) {
                return;
            }
            CharSequence summary = createSummary();
            callSupperSetText(summary);
            setOnClickListener(new OnClick(summary));
        }
    };

    public ExpandableTextView(Context context) {
        this(context, null, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableTextView);
        moreText = a.getString(R.styleable.ExpandableTextView_rmtMoreText);
        lessText = a.getString(R.styleable.ExpandableTextView_rmtLessText);
        moreColor = a.getInteger(R.styleable.ExpandableTextView_rmtMoreColor, Color.BLUE);
        lessColor = a.getInteger(R.styleable.ExpandableTextView_rmtLessColor, Color.BLUE);
        a.recycle();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        this.text = text.toString();
        this.bufferType = type;
        addOnGlobalLayoutListener();
        super.setText(text, type);
    }

    @Override
    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
        addOnGlobalLayoutListener();
        super.setMaxLines(maxLines);
    }

    private void addOnGlobalLayoutListener() {
        if (listener == null || maxLines < 1 || text == null) {
            return;
        }
        getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    /**
     * 设置展开状态变化事件监听器。
     *
     * @param listener 展开状态变化事件监听器
     */
    public void setOnExpandChangeListener(OnExpandChangeListener listener) {
        this.onExpandChangeListener = listener;
    }

    private Spanned create(CharSequence content, String label, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(label);
        builder.setSpan(new ForegroundColorSpan(color), 3, label.length(), SPAN_INCLUSIVE_EXCLUSIVE);
        return new SpannableStringBuilder(content).append(builder);
    }

    private CharSequence createContent() {
        if (lessText == null || lessText.length() == 0) {
            return text;
        }
        return create(text, lessText, lessColor);
    }

    private CharSequence createSummary() {
        if (this.moreText == null || this.moreText.length() == 0) {
            return text;
        }
        String moreText = "..." + this.moreText;
        Layout layout = getLayout();
        int start = layout.getLineStart(maxLines - 1);
        int end = layout.getLineEnd(maxLines - 1) - start;
        CharSequence content = text.replaceAll("\r\n", " ").subSequence(start, text.replaceAll("\r\n", " ").length());
        float moreWidth = getPaint().measureText(moreText, 0, moreText.length());
        float maxWidth = layout.getWidth() - moreWidth - 10;
        int len = getPaint().breakText(content, 0, content.length(), true, maxWidth, null);
        if (content.length() > end - 1 && content.charAt(end - 1) == '\n') {
            end = end - 1;
        }
        len = Math.min(len, end);
        return create(text.replaceAll("\r\n", " ").subSequence(0, start + len), moreText, moreColor);
    }

    private void callSupperSetText(CharSequence text) {
        super.setText(text, bufferType);
    }

    private class OnClick implements View.OnClickListener {

        boolean expand = false;
        CharSequence summary;
        CharSequence content;

        OnClick(CharSequence s) {
            this.summary = s;
        }

        @Override
        public void onClick(View view) {
            if (!expand) {
                if (content == null) {
                    content = createContent();
                }
                ExpandableTextView.super.setMaxLines(MAX_VALUE);
                callSupperSetText(content);
                if (onExpandChangeListener != null) {
                    onExpandChangeListener.onExpandChange(ExpandableTextView.this, true);
                }
            }
            expand = true;
        }
    }

    /**
     * 展开状态变化事件监听器。
     */
    public interface OnExpandChangeListener {

        /**
         * 处理展开变化事件。
         *
         * @param view 视图
         * @param expand true：已展开；false：已收起。
         */
        void onExpandChange(ExpandableTextView view, boolean expand);
    }
}
