<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet"
          target="_blank" target="_blank" target="_blank" target="_blank" target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<span id="load_date">${code.date}</span>
<#if code.views gt -1 && code.isRestrictedByViews()>
    <span id="views_restriction">${code.views} more view(s) allowed</span>
</#if>
<#if code.time gt 0>
    <span id="time_restriction">The code will be available for ${code.time} second(s)</span>
</#if>
<pre id="code_snippet"><code>${code.code}</code></pre>
</body>
</html>
