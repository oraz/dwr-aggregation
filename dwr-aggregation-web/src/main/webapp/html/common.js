'use strict';

jQuery(document).ready(function ($) {

    usersManager.getAllUsers(function (users) {
        var list$ = $('<ul/>', { id: 'users'});
        $.each(users, function () {
            $('<li/>', { text: this }).appendTo(list$);
        });
        list$.appendTo(document.body);
    });

    citiesManager.getAllCities(function (cities) {
        var list$ = $('<ul/>', { id: 'cities'});
        $.each(cities, function () {
            $('<li/>', { text: this }).appendTo(list$);
        });
        list$.appendTo(document.body);
    });

});