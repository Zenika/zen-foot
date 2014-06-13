(function () {

    var isCookieEnabled = function () {
        // Quick test if browser has cookieEnabled host property
        if (navigator.cookieEnabled) return true;
        // Create cookie
        document.cookie = 'cookietest=1';
        var ret = document.cookie.indexOf('cookietest=') != -1;
        // Delete cookie
        document.cookie = 'cookietest=1; expires=Thu, 01-Jan-1970 00:00:01 GMT';
        return ret;
    };

    if (!isCookieEnabled()) {
        document.getElementById('view').innerHTML = '<p class="alert alert-warning text-center">Il semble que les cookies soient désactivés dans votre navigateur. Vous devez modifier votre paramétrage et pour avoir accès à zenfoot.</p>';
    }
})();