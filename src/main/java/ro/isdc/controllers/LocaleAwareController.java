package ro.isdc.controllers;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class LocaleAwareController {
	private static List<String> supportedLanguages = new ArrayList<String>();

	public LocaleAwareController() {
	}

	@ModelAttribute("supportedLanguages")
	public List<String> getCurrentLocale(final HttpServletRequest request, final String lang, final Model model) {
		String userLang = lang;
		if (userLang == null || userLang.trim().length() == 0) {
			userLang = Locale.getDefault().getLanguage();
		}
		Locale locale = new Locale(userLang.trim().toUpperCase());
		System.out.println("The new locale is: " + userLang);
		request.setAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME, locale);
		model.addAttribute("currentLanguage", userLang);
		return LocaleAwareController.supportedLanguages;
	}

	public static void loadSupportedLanguages(ServletContext ctx, String languageFolder) {
		String webappRoot = ctx.getRealPath(languageFolder);
		File classPathFolder = new File(webappRoot);
		classPathFolder.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String fileName) {
				String langCode = null;
				boolean found = false;
				try {
					Pattern rex = Pattern.compile("^messages_([a-z]{2})(?:[A-Z]{2})?\\.properties$");
					Matcher matcher = rex.matcher(fileName);
					found = matcher.find();
					if (found) {
						langCode = matcher.group(1);
						LocaleAwareController.supportedLanguages.add(langCode);
					}
				} catch (PatternSyntaxException ex) {
					found = false;
				}
				return found;
			}
		});
	}

}
