import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 自定义字间距的EditText
 * 
 * @author 工藤一号 gougou@16fan.com
 * @date 2017年6月13日 上午10:27:00
 */
public class SpacEditText extends EditText {
	private float letterSpacing = LetterSpacing.BIGGEST;
	private CharSequence originalText = "";

	public SpacEditText(Context context) {
		super(context);
	}

	public SpacEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		originalText = super.getText();
		applyLetterSpacing();
		this.invalidate();
	}

	public SpacEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public float getLetterSpacing() {
		return letterSpacing;
	}

	public void setLetterSpacing(float letterSpacing) {
		this.letterSpacing = letterSpacing;
		applyLetterSpacing();
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		originalText = text;
		applyLetterSpacing();
	}

	@Override
	public Editable getText() {
		Editable editable = new SpannableStringBuilder(originalText);
		return editable;
	}

	/**
	 * 字距为任何字符串（技术上，一个简单的方法为CharSequence不使用）的TextView
	 */
	private void applyLetterSpacing() {
		if (this == null || this.originalText == null)
			return;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < originalText.length(); i++) {
			String c = "" + originalText.charAt(i);
			builder.append(c.toLowerCase());
			if (i + 1 < originalText.length()) {
				builder.append("\u00A0");
			}
		}
		SpannableString finalText = new SpannableString(builder.toString());
		if (builder.toString().length() > 1) {
			for (int i = 1; i < builder.toString().length(); i += 2) {
				finalText.setSpan(new ScaleXSpan((letterSpacing + 1) / 10), i, i + 1,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		super.setText(finalText, BufferType.SPANNABLE);
	}

	public class LetterSpacing {
		public final static float NORMAL = 0;
		public final static float NORMALBIG = (float) 0.025;
		public final static float BIG = (float) 0.05;
		public final static float BIGGEST = (float) 0.2;
	}
}
