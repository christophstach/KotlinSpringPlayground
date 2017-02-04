/*
 * Copyright (c) 2017 Christoph Stach <christoph.stach@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
"use strict";

class index {
    constructor($, apiUrl) {
        this.$ = $;
        this.apiUrl = apiUrl;
        this.claims = [];
        this.jwt = localStorage.getItem("jwtToken");

        if(this.jwt) {
            this.claims = this.tokenToClaims(this.jwt);
        }

        this.modifyJqueryAjaxBehavior();
    }

    modifyJqueryAjaxBehavior() {
        let get = this.$.get;
        let post = this.$.post;
        let ajax = this.$.ajax;

        let beforeSend = (xhr) => {
            if(this.jwt != null) {
                xhr.setRequestHeader("Authorization", "Bearer " + this.jwt);
            }
        };

        this.$.get = (params) => {
            params.beforeSend = beforeSend;
            return get(params);
        };

        this.$.post = (params) => {
            return post(params);
        };

        this.$.ajax = (params) => {
            return ajax(params);
        };
    }

    tokenToClaims(token) {
        let jwt = token.split(".");
        return atob(jwt[1]);
    }

    login() {
        let me = this;

        return this.$.get({
            url: this.apiUrl + "/auth/login",
            success: (data) => {
                console.info(data);

                this.$('#token').text(data.token);
                this.$('#expires').text(data.expires);

                this.jwt = data.token;
                localStorage.setItem("jwtToken", this.jwt);
                this.claims = this.tokenToClaims(this.jwt);

                console.log(this.claims);
            }
        });
    }

    logout() {
        let me = this;

        return this.$.get({
            url: this.apiUrl + "/auth/logout",
            success: (data) => {
                console.info(data);
            }
        });
    }

    getStudents() {
        let me = this;

        return this.$.get({
            url: this.apiUrl + "/student",
            success: (data) => {
                console.info(data);
            }
        });
    }

    getCourses() {
        let me = this;

        return this.$.get({
            url: this.apiUrl + "/course",
            success: (data) => {
                console.info(data);
            }
        });
    }
}