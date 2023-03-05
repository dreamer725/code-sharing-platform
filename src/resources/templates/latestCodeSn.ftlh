<html lang="en">
<head>
    <title>Latest</title>
    <link rel="stylesheet"
          target="_blank" target="_blank" target="_blank" target="_blank" target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<h1>Latest code snippets</h1>
<table>
    <#list code as c>
        <span id="load_date">${c.date}</span>
        <pre id="code_snippet"><code>${c.code}</code></pre>
    </#list>
</table>
</body>
</html>