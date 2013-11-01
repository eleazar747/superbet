$(function () {
    $('fieldset.collapsible > legend').prepend('<i class="icon-plus"></i>');
    $('fieldset.collapsible > legend').click(function () {
        var $divs = $(this).siblings();
        $divs.toggle();

        $(this).find('i').removeClass().addClass(function () {
            return ($divs.is(':visible')) ? 'icon-minus' : 'icon-plus';
        });
    });
    $('fieldset.collapsible > legend').siblings().toggle();
});