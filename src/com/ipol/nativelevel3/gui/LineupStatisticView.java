package com.ipol.nativelevel3.gui;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.ipol.nativelevel3.R;

public class LineupStatisticView extends View {
	
	private static int MARGIN_MIDDLE;
	private static int NORMAL_VIEW_HEIGHT;
	private static int WHITE_TEXT_SIZE;
	private static int POSITION_TEXT_SIZE;
	private static int POSITION_TEXT_MARGIN_SIDE;
	private static int PLAYER_TEXT_MARGIN_BOTTOM;
	private static int NUMBER_TEXT_MARGIN;
	private static int NAME_TEXT_MARGIN;
	private static int UNDERLINE_HEIGHT;
	private static int UNDERLINE_MARGIN_INSIDE;
	private static int UNDERLINE_MARGIN_OUTSIDE;
	private static int GRADIENT_HEIGHT;
	private static int ARROW_MARGIN_SIDE;
	private static int MINUTE_MARGIN_SIDE;
	private static int SUBSTITUTION_WIDTH;
	private static int SUBSTITUTION_HEIGHT;
	
	private static int HOME_RIGHT;
	private static int GUEST_LEFT;
	
	private ArrayList<LineupPlayerData> goalHome;
	private ArrayList<LineupPlayerData> defenseHome;
	private ArrayList<LineupPlayerData> middleHome;
	private ArrayList<LineupPlayerData> offenseHome;
	private ArrayList<LineupPlayerData> goalGuest;
	private ArrayList<LineupPlayerData> defenseGuest;
	private ArrayList<LineupPlayerData> middleGuest;
	private ArrayList<LineupPlayerData> offenseGuest;
	
	private Paint backgroundPaint;
	private Paint blackPaint;
	private Paint gradientPaint;
	private TextPaint whiteTextPaint;
	private TextPaint grayTextPaint;
	
	private Bitmap arrowBitmap;
	private Bitmap outBitmap;
	
	int goalHomeBottom;
	int goalGuestBottom;
	int defenseHomeBottom;
	int defenseGuestBottom;
	int middleHomeBottom;
	int middleGuestBottom;
	int offenseHomeBottom;
	int offenseGuestBottom;

	public LineupStatisticView(Context context) {
		super(context);
		initView(context);
	}

	public LineupStatisticView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public LineupStatisticView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	public void initView(Context context) {
		initValues();
		initPaints();
		goalHome = new ArrayList<LineupPlayerData>();
		defenseHome = new ArrayList<LineupPlayerData>();
		middleHome = new ArrayList<LineupPlayerData>();
		offenseHome = new ArrayList<LineupPlayerData>();
		goalGuest = new ArrayList<LineupPlayerData>();
		defenseGuest = new ArrayList<LineupPlayerData>();
		middleGuest = new ArrayList<LineupPlayerData>();
		offenseGuest = new ArrayList<LineupPlayerData>();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth();
		int previousBottom;
		if (goalHome.size() > 0) {
			previousBottom = 0;
			canvas.drawRect(0, previousBottom, HOME_RIGHT, goalHomeBottom, backgroundPaint);
			canvas.drawRect(0, goalHomeBottom - GRADIENT_HEIGHT, HOME_RIGHT, goalHomeBottom, gradientPaint);
			canvas.drawText("T", POSITION_TEXT_MARGIN_SIDE, previousBottom + POSITION_TEXT_SIZE, grayTextPaint);
			grayTextPaint.setTextSize(WHITE_TEXT_SIZE);
			for (int i = 0; i< goalHome.size(); i++) {
				float playerInfoBottom = previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - PLAYER_TEXT_MARGIN_BOTTOM;
				LineupPlayerData player = goalHome.get(i);
				canvas.drawText(player.getNumber(), HOME_RIGHT - NUMBER_TEXT_MARGIN - whiteTextPaint.measureText(player.getNumber()), playerInfoBottom, grayTextPaint);
				float nameWidth = whiteTextPaint.measureText(player.getName());
				float nameOutsidePosX = HOME_RIGHT - NAME_TEXT_MARGIN - nameWidth;
				canvas.drawText(player.getName(), nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				if (player.hasLink) {
					nameOutsidePosX -= ARROW_MARGIN_SIDE + arrowBitmap.getWidth();
					canvas.drawBitmap(arrowBitmap, nameOutsidePosX, playerInfoBottom - arrowBitmap.getHeight(), null);
				}
				if (player.getMinute() != null && !player.getMinute().equals("")) {
					nameOutsidePosX -= MINUTE_MARGIN_SIDE + whiteTextPaint.measureText("(");
					canvas.drawText("(", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
					nameOutsidePosX -= outBitmap.getWidth();
					canvas.drawBitmap(outBitmap, nameOutsidePosX, playerInfoBottom - outBitmap.getHeight(), null);
					nameOutsidePosX -= whiteTextPaint.measureText(player.getMinute() + ".)");
					canvas.drawText(player.getMinute() + ".)", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				}
				if (i < goalHome.size() - 1)
					canvas.drawRect(UNDERLINE_MARGIN_OUTSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - UNDERLINE_HEIGHT, HOME_RIGHT - UNDERLINE_MARGIN_INSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT, blackPaint);
			}
			grayTextPaint.setTextSize(POSITION_TEXT_SIZE);
		}
		if (goalGuest.size() > 0) {
			previousBottom = 0;
			canvas.drawRect(GUEST_LEFT, previousBottom, width, goalGuestBottom, backgroundPaint);
			canvas.drawRect(GUEST_LEFT, goalGuestBottom - GRADIENT_HEIGHT, width, goalGuestBottom, gradientPaint);
			canvas.drawText("T", width - POSITION_TEXT_MARGIN_SIDE - grayTextPaint.measureText("T"), previousBottom + POSITION_TEXT_SIZE, grayTextPaint);
			grayTextPaint.setTextSize(WHITE_TEXT_SIZE);
			for (int i = 0; i< goalGuest.size(); i++) {
				float playerInfoBottom = previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - PLAYER_TEXT_MARGIN_BOTTOM;
				LineupPlayerData player = goalGuest.get(i);
				canvas.drawText(player.getNumber(), GUEST_LEFT + NUMBER_TEXT_MARGIN, playerInfoBottom, grayTextPaint);
				float nameWidth = whiteTextPaint.measureText(player.getName());
				float nameOutsidePosX = GUEST_LEFT + NAME_TEXT_MARGIN + nameWidth;
				canvas.drawText(player.getName(), GUEST_LEFT + NAME_TEXT_MARGIN, playerInfoBottom, whiteTextPaint);
				if (player.hasLink) {
					canvas.drawBitmap(arrowBitmap, nameOutsidePosX + ARROW_MARGIN_SIDE, playerInfoBottom - arrowBitmap.getHeight(), null);
					nameOutsidePosX += ARROW_MARGIN_SIDE + arrowBitmap.getWidth();
				}
				if (player.getMinute() != null && !player.getMinute().equals("")) {
					nameOutsidePosX += MINUTE_MARGIN_SIDE;
					canvas.drawText("(", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
					nameOutsidePosX += whiteTextPaint.measureText("(");
					canvas.drawBitmap(outBitmap, nameOutsidePosX, playerInfoBottom - outBitmap.getHeight(), null);
					nameOutsidePosX += outBitmap.getWidth();
					canvas.drawText(player.getMinute() + ".)", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				}
				if (i < goalGuest.size() - 1)
					canvas.drawRect(GUEST_LEFT + UNDERLINE_MARGIN_INSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - UNDERLINE_HEIGHT, width - UNDERLINE_MARGIN_OUTSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT, blackPaint);
			}
			grayTextPaint.setTextSize(POSITION_TEXT_SIZE);
		}
		
		if (defenseHome.size() > 0) {
			previousBottom = goalHomeBottom;
			canvas.drawRect(0, previousBottom, HOME_RIGHT, defenseHomeBottom, backgroundPaint);
			canvas.drawRect(0, defenseHomeBottom - GRADIENT_HEIGHT, HOME_RIGHT, defenseHomeBottom, gradientPaint);
			canvas.drawText("A", POSITION_TEXT_MARGIN_SIDE, previousBottom + POSITION_TEXT_SIZE, grayTextPaint);
			grayTextPaint.setTextSize(WHITE_TEXT_SIZE);
			for (int i = 0; i< defenseHome.size(); i++) {
				float playerInfoBottom = previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - PLAYER_TEXT_MARGIN_BOTTOM;
				LineupPlayerData player = defenseHome.get(i);
				canvas.drawText(player.getNumber(), HOME_RIGHT - NUMBER_TEXT_MARGIN - whiteTextPaint.measureText(player.getNumber()), playerInfoBottom, grayTextPaint);
				float nameWidth = whiteTextPaint.measureText(player.getName());
				float nameOutsidePosX = HOME_RIGHT - NAME_TEXT_MARGIN - nameWidth;
				canvas.drawText(player.getName(), nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				if (player.hasLink) {
					nameOutsidePosX -= (ARROW_MARGIN_SIDE + arrowBitmap.getWidth());
					canvas.drawBitmap(arrowBitmap, nameOutsidePosX, playerInfoBottom - arrowBitmap.getHeight(), null);
				}
				if (player.getMinute() != null && !player.getMinute().equals("")) {

				}
				if (i < defenseHome.size() - 1)
					canvas.drawRect(UNDERLINE_MARGIN_OUTSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - UNDERLINE_HEIGHT, HOME_RIGHT - UNDERLINE_MARGIN_INSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT, blackPaint);
			}
			grayTextPaint.setTextSize(POSITION_TEXT_SIZE);
		}
		if (defenseGuest.size() > 0) {
			previousBottom = goalGuestBottom;
			canvas.drawRect(GUEST_LEFT, previousBottom, width, defenseGuestBottom, backgroundPaint);
			canvas.drawRect(GUEST_LEFT, defenseGuestBottom - GRADIENT_HEIGHT, width, defenseGuestBottom, gradientPaint);
			canvas.drawText("A", width - POSITION_TEXT_MARGIN_SIDE - grayTextPaint.measureText("A"), previousBottom + POSITION_TEXT_SIZE, grayTextPaint);
			grayTextPaint.setTextSize(WHITE_TEXT_SIZE);
			for (int i = 0; i< defenseGuest.size(); i++) {
				float playerInfoBottom = previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - PLAYER_TEXT_MARGIN_BOTTOM;
				LineupPlayerData player = defenseGuest.get(i);
				canvas.drawText(player.getNumber(), GUEST_LEFT + NUMBER_TEXT_MARGIN, playerInfoBottom, grayTextPaint);
				float nameWidth = whiteTextPaint.measureText(player.getName());
				float nameOutsidePosX = GUEST_LEFT + NAME_TEXT_MARGIN + nameWidth;
				canvas.drawText(player.getName(), GUEST_LEFT + NAME_TEXT_MARGIN, playerInfoBottom, whiteTextPaint);
				if (player.hasLink) {
					canvas.drawBitmap(arrowBitmap, nameOutsidePosX + ARROW_MARGIN_SIDE, playerInfoBottom - arrowBitmap.getHeight(), null);
					nameOutsidePosX += ARROW_MARGIN_SIDE + arrowBitmap.getWidth();
				}
				if (player.getMinute() != null && !player.getMinute().equals("")) {
					nameOutsidePosX += MINUTE_MARGIN_SIDE;
					canvas.drawText("(", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
					nameOutsidePosX += whiteTextPaint.measureText("(");
					canvas.drawBitmap(outBitmap, nameOutsidePosX, playerInfoBottom - outBitmap.getHeight(), null);
					nameOutsidePosX += outBitmap.getWidth();
					canvas.drawText(player.getMinute() + ".)", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				}
				if (i < defenseGuest.size() - 1)
					canvas.drawRect(GUEST_LEFT + UNDERLINE_MARGIN_INSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - UNDERLINE_HEIGHT, width - UNDERLINE_MARGIN_OUTSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT, blackPaint);
			}
			grayTextPaint.setTextSize(POSITION_TEXT_SIZE);
		}
		
		if (middleHome.size() > 0) {
			previousBottom = defenseHomeBottom;
			canvas.drawRect(0, previousBottom, HOME_RIGHT, middleHomeBottom, backgroundPaint);
			canvas.drawRect(0, middleHomeBottom - GRADIENT_HEIGHT, HOME_RIGHT, middleHomeBottom, gradientPaint);
			canvas.drawText("M", POSITION_TEXT_MARGIN_SIDE, previousBottom + POSITION_TEXT_SIZE, grayTextPaint);
			grayTextPaint.setTextSize(WHITE_TEXT_SIZE);
			for (int i = 0; i< middleHome.size(); i++) {
				float playerInfoBottom = previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - PLAYER_TEXT_MARGIN_BOTTOM;
				LineupPlayerData player = middleHome.get(i);
				canvas.drawText(player.getNumber(), HOME_RIGHT - NUMBER_TEXT_MARGIN - whiteTextPaint.measureText(player.getNumber()), playerInfoBottom, grayTextPaint);
				float nameWidth = whiteTextPaint.measureText(player.getName());
				float nameOutsidePosX = HOME_RIGHT - NAME_TEXT_MARGIN - nameWidth;
				canvas.drawText(player.getName(), nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				if (player.hasLink) {
					nameOutsidePosX -= (ARROW_MARGIN_SIDE + arrowBitmap.getWidth());
					canvas.drawBitmap(arrowBitmap, nameOutsidePosX, playerInfoBottom - arrowBitmap.getHeight(), null);
				}
				if (player.getMinute() != null && !player.getMinute().equals("")) {
					nameOutsidePosX -= MINUTE_MARGIN_SIDE + whiteTextPaint.measureText(player.getMinute() + ".)");
					canvas.drawText(player.getMinute() + ".)", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
					nameOutsidePosX -= outBitmap.getWidth();
					canvas.drawBitmap(outBitmap, nameOutsidePosX, playerInfoBottom - outBitmap.getHeight(), null);
					nameOutsidePosX -= whiteTextPaint.measureText("(");
					canvas.drawText("(", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				}
				if (i < middleHome.size() - 1)
					canvas.drawRect(UNDERLINE_MARGIN_OUTSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - UNDERLINE_HEIGHT, HOME_RIGHT - UNDERLINE_MARGIN_INSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT, blackPaint);
			}
			grayTextPaint.setTextSize(POSITION_TEXT_SIZE);
		}
		if (middleGuest.size() > 0) {
			previousBottom = defenseGuestBottom;
			canvas.drawRect(GUEST_LEFT, previousBottom, width, middleGuestBottom, backgroundPaint);
			canvas.drawRect(GUEST_LEFT, middleGuestBottom - GRADIENT_HEIGHT, width, middleGuestBottom, gradientPaint);
			canvas.drawText("M", width - POSITION_TEXT_MARGIN_SIDE - grayTextPaint.measureText("M"), previousBottom + POSITION_TEXT_SIZE, grayTextPaint);
			grayTextPaint.setTextSize(WHITE_TEXT_SIZE);
			for (int i = 0; i< middleGuest.size(); i++) {
				float playerInfoBottom = previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - PLAYER_TEXT_MARGIN_BOTTOM;
				LineupPlayerData player = middleGuest.get(i);
				canvas.drawText(player.getNumber(), GUEST_LEFT + NUMBER_TEXT_MARGIN, playerInfoBottom, grayTextPaint);
				float nameWidth = whiteTextPaint.measureText(player.getName());
				float nameOutsidePosX = GUEST_LEFT + NAME_TEXT_MARGIN + nameWidth;
				canvas.drawText(player.getName(), GUEST_LEFT + NAME_TEXT_MARGIN, playerInfoBottom, whiteTextPaint);
				if (player.hasLink) {
					canvas.drawBitmap(arrowBitmap, nameOutsidePosX + ARROW_MARGIN_SIDE, playerInfoBottom - arrowBitmap.getHeight(), null);
					nameOutsidePosX += ARROW_MARGIN_SIDE + arrowBitmap.getWidth();
				}
				if (player.getMinute() != null && !player.getMinute().equals("")) {
					nameOutsidePosX += MINUTE_MARGIN_SIDE;
					canvas.drawText("(", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
					nameOutsidePosX += whiteTextPaint.measureText("(");
					canvas.drawBitmap(outBitmap, nameOutsidePosX, playerInfoBottom - outBitmap.getHeight(), null);
					nameOutsidePosX += outBitmap.getWidth();
					canvas.drawText(player.getMinute() + ".)", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				}
				if (i < middleGuest.size() - 1)
					canvas.drawRect(GUEST_LEFT + UNDERLINE_MARGIN_INSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - UNDERLINE_HEIGHT, width - UNDERLINE_MARGIN_OUTSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT, blackPaint);
			}
			grayTextPaint.setTextSize(POSITION_TEXT_SIZE);
		}
		
		if (offenseHome.size() > 0) {
			previousBottom = middleHomeBottom;
			canvas.drawRect(0, previousBottom, HOME_RIGHT, offenseHomeBottom, backgroundPaint);
			canvas.drawRect(0, offenseHomeBottom - GRADIENT_HEIGHT, HOME_RIGHT, offenseHomeBottom, gradientPaint);
			canvas.drawText("S", POSITION_TEXT_MARGIN_SIDE, previousBottom + POSITION_TEXT_SIZE, grayTextPaint);
			grayTextPaint.setTextSize(WHITE_TEXT_SIZE);
			for (int i = 0; i< offenseHome.size(); i++) {
				float playerInfoBottom = previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - PLAYER_TEXT_MARGIN_BOTTOM;
				LineupPlayerData player = offenseHome.get(i);
				canvas.drawText(player.getNumber(), HOME_RIGHT - NUMBER_TEXT_MARGIN - whiteTextPaint.measureText(player.getNumber()), playerInfoBottom, grayTextPaint);
				float nameWidth = whiteTextPaint.measureText(player.getName());
				float nameOutsidePosX = HOME_RIGHT - NAME_TEXT_MARGIN - nameWidth;
				canvas.drawText(player.getName(), nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				if (player.hasLink) {
					nameOutsidePosX -= (ARROW_MARGIN_SIDE + arrowBitmap.getWidth());
					canvas.drawBitmap(arrowBitmap, nameOutsidePosX, playerInfoBottom - arrowBitmap.getHeight(), null);
				}
				if (player.getMinute() != null && !player.getMinute().equals("")) {

				}
				if (i < offenseHome.size() - 1)
					canvas.drawRect(UNDERLINE_MARGIN_OUTSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - UNDERLINE_HEIGHT, HOME_RIGHT - UNDERLINE_MARGIN_INSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT, blackPaint);
			}
			grayTextPaint.setTextSize(POSITION_TEXT_SIZE);
		}
		if (offenseGuest.size() > 0) {
			previousBottom = middleGuestBottom;
			canvas.drawRect(GUEST_LEFT, previousBottom, width, offenseGuestBottom, backgroundPaint);
			canvas.drawRect(GUEST_LEFT, offenseGuestBottom - GRADIENT_HEIGHT, width, offenseGuestBottom, gradientPaint);
			canvas.drawText("S", width - POSITION_TEXT_MARGIN_SIDE - grayTextPaint.measureText("S"), previousBottom + POSITION_TEXT_SIZE, grayTextPaint);
			grayTextPaint.setTextSize(WHITE_TEXT_SIZE);
			for (int i = 0; i< offenseGuest.size(); i++) {
				float playerInfoBottom = previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - PLAYER_TEXT_MARGIN_BOTTOM;
				LineupPlayerData player = offenseGuest.get(i);
				canvas.drawText(player.getNumber(), GUEST_LEFT + NUMBER_TEXT_MARGIN, playerInfoBottom, grayTextPaint);
				float nameWidth = whiteTextPaint.measureText(player.getName());
				float nameOutsidePosX = GUEST_LEFT + NAME_TEXT_MARGIN + nameWidth;
				canvas.drawText(player.getName(), GUEST_LEFT + NAME_TEXT_MARGIN, playerInfoBottom, whiteTextPaint);
				if (player.hasLink) {
					canvas.drawBitmap(arrowBitmap, nameOutsidePosX + ARROW_MARGIN_SIDE, playerInfoBottom - arrowBitmap.getHeight(), null);
					nameOutsidePosX += ARROW_MARGIN_SIDE + arrowBitmap.getWidth();
				}
				if (player.getMinute() != null && !player.getMinute().equals("")) {
					nameOutsidePosX += MINUTE_MARGIN_SIDE;
					canvas.drawText("(", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
					nameOutsidePosX += whiteTextPaint.measureText("(");
					canvas.drawBitmap(outBitmap, nameOutsidePosX, playerInfoBottom - outBitmap.getHeight(), null);
					nameOutsidePosX += outBitmap.getWidth();
					canvas.drawText(player.getMinute() + ".)", nameOutsidePosX, playerInfoBottom, whiteTextPaint);
				}
				if (i < offenseGuest.size() - 1)
					canvas.drawRect(GUEST_LEFT + UNDERLINE_MARGIN_INSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT - UNDERLINE_HEIGHT, width - UNDERLINE_MARGIN_OUTSIDE, previousBottom + (i + 1) * NORMAL_VIEW_HEIGHT, blackPaint);
			}
			grayTextPaint.setTextSize(POSITION_TEXT_SIZE);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int desiredWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 200, getResources()
						.getDisplayMetrics());
		int desiredHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		// Measure Width
		if (widthMode == MeasureSpec.EXACTLY) {
			// Must be this size
			width = widthSize;
		} else if (widthMode == MeasureSpec.AT_MOST) {
			// Can't be bigger than...
			width = Math.min(desiredWidth, widthSize);
		} else {
			// Be whatever you want
			width = desiredWidth;
		}
		
		// Measure Height
		if (heightMode == MeasureSpec.EXACTLY) {
			// Must be this size
			height = heightSize;
		} else if (heightMode == MeasureSpec.AT_MOST) {
			// Can't be bigger than...
			height = Math.min(desiredHeight, heightSize);
		} else {
			// Be whatever you want
			height = desiredHeight;
		}

		// MUST CALL THIS
		setMeasuredDimension(width, height);
	}
	
	public void initValues() {
		Resources res = getResources();
		MARGIN_MIDDLE = (int) res.getDimension(R.dimen.lineup_statistics_margin_middle);
		NORMAL_VIEW_HEIGHT = (int) res.getDimension(R.dimen.lineup_statistics_normal_view_height);
		WHITE_TEXT_SIZE = (int) res.getDimension(R.dimen.lineup_statistics_white_text_size);
		POSITION_TEXT_SIZE = (int) res.getDimension(R.dimen.lineup_statistics_position_text_size);
		POSITION_TEXT_MARGIN_SIDE = (int) res.getDimension(R.dimen.lineup_statistics_position_text_margin_side);
		PLAYER_TEXT_MARGIN_BOTTOM = (int) res.getDimension(R.dimen.lineup_statistics_player_text_margin_bottom);
		NUMBER_TEXT_MARGIN = (int) res.getDimension(R.dimen.lineup_statistics_number_text_margin);
		NAME_TEXT_MARGIN = (int) res.getDimension(R.dimen.lineup_statistics_name_text_margin);
		UNDERLINE_HEIGHT = (int) res.getDimension(R.dimen.lineup_statistics_underline_height);
		UNDERLINE_MARGIN_INSIDE = (int) res.getDimension(R.dimen.lineup_statistics_underline_margin_inside);
		UNDERLINE_MARGIN_OUTSIDE = (int) res.getDimension(R.dimen.lineup_statistics_underline_margin_outside);
		GRADIENT_HEIGHT = (int) res.getDimension(R.dimen.lineup_statistics_gradient_height);
		ARROW_MARGIN_SIDE = (int) res.getDimension(R.dimen.lineup_statistics_arrow_margin_side);
		MINUTE_MARGIN_SIDE = (int) res.getDimension(R.dimen.lineup_statistics_minute_margin_side);
		SUBSTITUTION_WIDTH = (int) res.getDimension(R.dimen.lineup_statistics_substitution_width);
		SUBSTITUTION_HEIGHT = (int) res.getDimension(R.dimen.lineup_statistics_substitution_height);
		
		arrowBitmap = BitmapFactory.decodeResource(res, R.drawable.arrow);
		Bitmap tempBitmap = BitmapFactory.decodeResource(res, R.drawable.change_out);
		outBitmap = Bitmap.createScaledBitmap(tempBitmap, SUBSTITUTION_WIDTH, SUBSTITUTION_HEIGHT, true);
		
		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
				HOME_RIGHT = (getWidth() - MARGIN_MIDDLE) / 2;
				GUEST_LEFT = HOME_RIGHT + MARGIN_MIDDLE;
			}
		});
	}
	
	public void initPaints() {
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.parseColor("#33676767"));
		
		blackPaint = new Paint();
		
		gradientPaint = new Paint();
		gradientPaint.setShader(new LinearGradient(0, 0, 0, GRADIENT_HEIGHT, Color.parseColor("#00000000"), Color.parseColor("#99000000"), Shader.TileMode.REPEAT));
		
		whiteTextPaint = new TextPaint();
		whiteTextPaint.setColor(Color.WHITE);
		whiteTextPaint.setTextSize(WHITE_TEXT_SIZE);
		whiteTextPaint.setFlags(Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		
		grayTextPaint = new TextPaint();
		grayTextPaint.setColor(Color.parseColor("#676767"));
		grayTextPaint.setTextSize(POSITION_TEXT_SIZE);
		grayTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
		grayTextPaint.setFlags(Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
	}
	
	public void setData(Map<String, Object> subnode) {
		
		
		goalHomeBottom = goalHome.size() * NORMAL_VIEW_HEIGHT;
		goalGuestBottom = goalGuest.size() * NORMAL_VIEW_HEIGHT;
		defenseHomeBottom = goalHomeBottom + defenseHome.size() * NORMAL_VIEW_HEIGHT;
		defenseGuestBottom = goalGuestBottom + defenseGuest.size() * NORMAL_VIEW_HEIGHT;
		middleHomeBottom = defenseHomeBottom + middleHome.size() * NORMAL_VIEW_HEIGHT;
		middleGuestBottom = defenseGuestBottom + middleGuest.size() * NORMAL_VIEW_HEIGHT;
		offenseHomeBottom = middleHomeBottom + offenseHome.size() * NORMAL_VIEW_HEIGHT;
		offenseGuestBottom = middleGuestBottom + offenseGuest.size() * NORMAL_VIEW_HEIGHT;
	}
	
	public void testCode() {
		// Torwart
		LineupPlayerData player = new LineupPlayerData("Weidenfeller", "1", "T", true);
		goalHome.add(player);
		player = new LineupPlayerData("Neuer", "1", "T", false);
		goalGuest.add(player);
		
		// Abwehr
		player = new LineupPlayerData("Durm", "37", "A", true);
		defenseHome.add(player);
		player = new LineupPlayerData("Friedrich", "2", "A", true);
		defenseHome.add(player);
		player = new LineupPlayerData("Gro§kreutz", "19", "A", true);
		defenseHome.add(player);
		player = new LineupPlayerData("Sokratis", "25", "A", true);
		defenseHome.add(player);
		
		player = new LineupPlayerData("Alaba", "27", "A", false);
		defenseGuest.add(player);
		player = new LineupPlayerData("Boateng", "17", "A", false);
		player.setMinute("64");
		defenseGuest.add(player);
		player = new LineupPlayerData("Dante", "4", "A", false);
		defenseGuest.add(player);
		player = new LineupPlayerData("Rafinha", "13", "A", false);
		player.setMinute("79");
		defenseGuest.add(player);
		
		// Mittelfeld
		player = new LineupPlayerData("Bender", "6", "M", true);
		player.setMinute("79");
		middleHome.add(player);
		player = new LineupPlayerData("Blaszczykowski", "16", "M", true);
		player.setMinute("71");
		middleHome.add(player);
		player = new LineupPlayerData("Mkhitaryan", "10", "M", true);
		player.setMinute("71");
		middleHome.add(player);
		player = new LineupPlayerData("Reus", "11", "M", true);
		middleHome.add(player);
		player = new LineupPlayerData("Sahin", "18", "M", true);
		middleHome.add(player);
		
		player = new LineupPlayerData("Javi Martinez", "8", "M", false);
		middleGuest.add(player);
		player = new LineupPlayerData("Kroos", "39", "M", false);
		middleGuest.add(player);
		player = new LineupPlayerData("Lahm", "21", "M", false);
		middleGuest.add(player);
		player = new LineupPlayerData("MŸller", "25", "M", false);
		middleGuest.add(player);
		player = new LineupPlayerData("Robben", "10", "M", false);
		middleGuest.add(player);
		
		// Sturm
		player = new LineupPlayerData("Lewandowski", "9", "S", true);
		offenseHome.add(player);
		
		player = new LineupPlayerData("Mandzukic", "9", "S", false);
		player.setMinute("56");
		offenseGuest.add(player);
		
		setData(null);
	}
	
	private class LineupPlayerData {
		
		private String name;
		private String number;
		private String position;
		private String id;
		private String minute;
		private boolean home;
		private boolean hasLink;
		
		public LineupPlayerData(String name, String number, String position, boolean home) {
			this.name = name;
			this.position = position;
			if (number.trim().equals("0"))
				this.number = "";
			else
				this.number = number;
			hasLink = true;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getMinute() {
			return minute;
		}

		public void setMinute(String minute) {
			this.minute = minute;
		}

		public boolean isHome() {
			return home;
		}

		public void setHome(boolean home) {
			this.home = home;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			if (number.trim().equals("0"))
				this.number = "";
			else
				this.number = number;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}
		
		public boolean hasLink() {
			return hasLink;
		}
		
		public void setHasLink(boolean hasLink) {
			this.hasLink = hasLink;
		}
		
	}
}
