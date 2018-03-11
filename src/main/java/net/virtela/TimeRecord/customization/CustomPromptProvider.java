package net.virtela.TimeRecord.customization;

import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomPromptProvider implements PromptProvider {
	
	@Value("${app.shell.prompt}")
	String customPrompt;

	@Override
	public AttributedString getPrompt() {
		return new AttributedString(customPrompt);
	}

}
