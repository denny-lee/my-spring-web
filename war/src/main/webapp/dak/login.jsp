<%@ page isELIgnored="false" %>
<html>
<head>
<title>Login</title>
</head>
<body>
<div class="container">
    <div data-bind="visible: showErrorMessage" class="alert alert-error">
        Invalid username/password.
    </div>
    <form class="form-signin" method="post" action="login.jsp">
        <h3 class="muted form-signin-heading">Please sign in</h3>
        <input type="text" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="text" class="input-block-level" placeholder="User name" name="username">
        <input type="password" class="input-block-level" placeholder="Password" name="password">
        <button class="btn btn-primary" type="submit">Sign in</button>
        <hr>
        <p class="text-info"><small>
            Log in as <strong>fabrice/fab123</strong> or <strong>paulson/bond</strong><br>
            See <code>WebSecurityConfig.java</code></small></p>
    </form>
</div><!-- /container -->

<script src="//cdnjs.cloudflare.com/ajax/libs/knockout/2.3.0/knockout-min.js"></script>

<script>
    ko.applyBindings({
        showErrorMessage : ko.computed(function() {
            var query = window.location.search;
            return query ? (query.indexOf('error') != -1) : false;
        })
    });
</script>
</body>
</html>
