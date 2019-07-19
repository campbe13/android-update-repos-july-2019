# Lab 04 Instructions

## This readme created using https://word-to-markdown.herokuapp.com/

Create a simple app that displays a country name and an associated image that are specific to a language.   The image will launch  google maps to locate the country when clicked.  Localise it for 5 languages/countries  (i.e. when it runs it displays the image and text associated with the locale without programmer intervention.)  There will be simple coding for this,  it is mostly  done in res(ource) directories.

1. Create a simple app UI that has a TextView and ImageButton. Use LinearLayout.

1. Populate default directories (res/values/ and res/drawable/ no localisation yet) with a country name string in values/strings.xml  and a country image in drawable/.  Your app may crash if you do not have a default on device/AVD start up.  Your  devices are factory set to  English US (en-rUS.)  Choose an english speaking country.
For example you could use a map of  Canada in the drawable directory named country.png and  Canada in &lt;string name=&quot;country&quot;&gt;Canada&lt;/string&gt; .
Or you could use a map of US in the drawable directory named country.png and  USA in &lt;string name=&quot;country&quot;&gt;USA&lt;/string&gt; .   Use the name string and drawable  in  your layout xml.

1. Add code to have the button launch google maps  showing a the country associated with the image, see [https://developer.android.com/guide/components/intents-common.html#Maps](https://developer.android.com/guide/components/intents-common.html#Maps)

_You will need to add the intent filter to the manifest and to use Uri.parse or Uri.builder to create your Uri.   Keep it simple, use country name not lat/long._

_Note you always need to parse a URI & encode any strings so it does not crash the request, for example:_

```java
  Uri   myUri1 = Uri.parse("geo:45.489374,-73.588298?q=" +  Uri.encode(“3040 Sherbrooke St W"));
  Uri   myUri2 = Uri.parse("geo:0,0?q=" + Uri.encode(“Montreal"));
```

[Full reference RFC for geo uri](https://tools.ietf.org/html/rfc5870)


1. Run your app at this point, to make sure it works.


2. English  is now your default, so  add other languages.  You will use French and a French speaking  country and choose two other languages.  Check the languages on your device before choosing. Search the web for an image or a map and/or a flag or something representing the languages you choose from [http://openclipart.org/](http://openclipart.org/) or another site; be sure to download a png image.

&quot;The language is defined by a two-letter  [ISO 639-1](http://www.loc.gov/standards/iso639-2/php/code_list.php) language code, optionally followed by a two letter  [ISO 3166-1-alpha-2](http://www.iso.org/iso/prods-services/iso3166ma/02iso-3166-code-lists/country_names_and_code_elements) region code (preceded by lowercase &quot;r&quot;). &quot; (en-rCA, fr-rCA, en-rGB, etc) Set up a value directory, string.xml file and string country entry for each language.
See [http://developer.android.com/guide/topics/resources/providing-resources.html#AlternativeResources](http://developer.android.com/guide/topics/resources/providing-resources.html#AlternativeResources)

1. For each alternate language create a res/drawable directory,  import your images; remember your image must have the same file name, different directory name.

1. For each alternate language create a res/values directory,  create a strings.xml for each with the appropriate text in the language.

1. When you run your app test each language by changing the locale on the device/AVD.  Change the language  either through the settings button  in the launcher or the command line:   [http://wiki.pcampbell.profweb.ca/index.php/Set\_the\_locale\_in\_Android](http://wiki.pcampbell.profweb.ca/index.php/Set_the_locale_in_Android)

 The command line is only possible on AVDs or rooted devices.

Once everything is working for each language modify your ImageButton so that the image fills the screen including when you rotate.

I have given you an example app  in this repo [apk](lab04-teachers-example.apk)    this is the behaviour I expect.

## Optional:

- Instead of going directly to google maps invoke another activity where you ask  for a location (city or address) then invoke google maps for location + country.
- Use a different text colour for each language/country (find out the flag colours and use one.)
- Convert the png images to 9  Patch (&lt;android sdk&gt;/tools/draw9patch.bat, see what happens when you test on various screen resolutions.
- Add countries that have the same language different region code (en-rGB, en-rCa en-rUS)   You will have to run some tests on an AVD as the SIM card is what defines the country AFAIK.  If you discover differently SVP let me know.  Or you may want to add further fuctionality to change the language and country programmatically.
- Have different layouts for landscape and portrait

## Helpful (?) Material

- [http://developer.android.com/training/basics/supporting-devices/languages.html](http://developer.android.com/training/basics/supporting-devices/languages.html)
- [http://developer.android.com/guide/topics/resources/localization.html](http://developer.android.com/guide/topics/resources/localization.html)
- [http://www.icanlocalize.com/site/tutorials/android-application-localization-tutorial/](http://www.icanlocalize.com/site/tutorials/android-application-localization-tutorial/)

- My repos on gitlab <https://gitlab.com/Android518-2018> see  localisation  &amp; resources
