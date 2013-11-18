'use strict';

function onLoad(handler) {
    if (document.addEventListener) {
        document.addEventListener('DOMContentLoaded', handler, false);
    } else {
        window.attachEvent('onload', handler);
    }
}

onLoad(function () {
    usersManager.getAllUsers({
        callback: createList,
        callbackArg: 'users'
    });

    citiesManager.getAllCities({
        callback: createList,
        callbackArg: 'cities'
    });

    function createList(data, elemId) {
        var list = document.createElement('ul');
        list.setAttribute('id', elemId);
        for (var i = 0, size = data.length; i < size; i++) {
            var item = document.createElement('li'),
                text = document.createTextNode(data[i]);
            item.appendChild(text);
            list.appendChild(item);
        }
        document.body.appendChild(list);
    }
});
