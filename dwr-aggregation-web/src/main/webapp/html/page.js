'use strict';

function onLoad(handler) {
    if (document.addEventListener) {
        document.addEventListener('DOMContentLoaded', handler, false);
    } else {
        window.attachEvent('onload', handler);
    }
}

onLoad(function () {
    assertTrue(typeof FirstFakeDTO !== 'undefined', 'FirstFakeDTO must be defined');
    assertTrue(typeof SecondFakeDTO === 'undefined', 'FirstFakeDTO must not be defined');

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

    function assertTrue(condition, msg) {
        if (!condition) {
            alert(msg);
        }
    }
});
