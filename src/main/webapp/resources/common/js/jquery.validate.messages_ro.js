/*
 * Default messages for the jQuery validation plugin.
 * Locale: RO (Romanian; Română)
 */
(function ($) {
	$.extend($.validator.messages, {   
		required: "Acest câmp este obligatoriu.",
	    minlength: $.format("Introduceţi minim {0} caractere"),
		maxlength: $.format("Introduceţi maxim {0} caractere"),
		equalTo: "Introduceţi aceeași parolă cu cea de mai sus",
		email: "Introduceţi o adresă de e-mail validă",
		remote: $.format("{0} nu este disponibilă"),
		url: "Te rugăm sa introduci o adresă URL validă.",
		date: "Te rugăm să introduci o dată corectă.",
		dateISO: "Te rugăm să introduci o dată (ISO) corectă.",
		number: "Te rugăm să introduci un număr întreg valid.",
		digits: "Te rugăm să introduci doar cifre.",
		creditcard: "Te rugăm să introduci un numar de carte de credit valid.",
		accept: "Te rugăm să introduci o valoare cu o extensie validă.",
		rangelength: $.validator.format("Te rugăm să introduci o valoare între {0} și {1} caractere."),
		range: $.validator.format("Te rugăm să introduci o valoare între {0} și {1}."),
		max: $.validator.format("Te rugăm să introduci o valoare egal sau mai mică decât {0}."),
		min: $.validator.format("Te rugăm să introduci o valoare egal sau mai mare decât {0}.")
	});
}(jQuery));