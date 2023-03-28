$(document).ready(function() {
    var is_scroll = true;
    var keyword = $('#keyword').val();
    var category = $('#category').val();
    var table = $('#dataTable').DataTable({
        // Set other DataTable options as needed
        lengthChange: false,
        searching : false,
        info : false,
        paging : false
    });
    $(window).scroll(function(){

        if ( Math.ceil($(window).scrollTop()) + $(window).height() >= $(document).height() && !is_end){
            if(is_scroll){
                page++;
                is_scroll = false;
            }
            location.href="/book/bookList?category="+category+"&keyword="+keyword+"&page="+page;
        }

    });
});