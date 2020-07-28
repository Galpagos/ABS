package ProjektGlowny.commons.Frames;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AskIntParams {

	private Integer mDefaultValue;
	private Integer mMinValue;
	private Integer mMaxValue;

}
