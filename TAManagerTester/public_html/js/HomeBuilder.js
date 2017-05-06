
function initCourseInfo() {
    var dataFile = "./js/HomeData.json";
    addTitleBanner(dataFile);

}


function addTitleBanner(dataFile){
  var titleID = $("#title");
  var banner = $("#banner");
  var courseDataFile = dataFile;
  $.getJSON(courseDataFile, function json) {
      var subject = json.subject;
      var number = json.number;
      var semester = json.semester;
      var year = json.year;
      var title = json.title;

      banner.append(subject + " " + number + " - " + semester + " " + year + "<br>" + title);

      titleID.append(subject + " " + number);

    addBannerImages(dataFile);
  });
}

function addBannerImages(dataFile){
    var bannerSchoolImage = $("#banner_school_image");
    var bannerSchoolImageHtml = "";
    bannerSchoolImageHtml = "<img alt=\"Stony Brook University\" class=\"sbu_navbar\" src=\"";
    var bannerImage = dataFile.banner_school_image;
    bannerSchoolImageHtml += bannerImage + "\">";
    bannerSchoolImage.append(bannerSchoolImageHtml);

    var leftFooterImage = $("#left_footer_image");
    var leftFooterImageHtml = "<img alt=\"SBU\" class=\"sunysb\" src=\"";
    var leftImage = dataFile.left_footer_image;
    leftFooterImageHtml += leftImage + "\" style=\"float:left\">";
    leftFooterImage.append(leftFooterImageHtml)

    var rightFooterImage = $("#right_footer_image");
    var rightFooterImageHtml = "<img alt=\"CS\" src=\"";
    var rightImage = dataFile.right_footer_image;
    rightFooterImageHtml = rightImage + "\" style=\"float:right\">";
    rightFooterImage.append(rightFooterImageHtml);

}
