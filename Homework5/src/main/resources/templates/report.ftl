<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Repository Report</title>
    <style>
        body { font-family: Arial, sans-serif; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>Repository Report</h1>
    <#if images?has_content>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Date</th>
                    <th>Tags</th>
                    <th>Path</th>
                </tr>
            </thead>
            <tbody>
                <#list images as image>
                    <tr>
                        <td>${image.name}</td>
                        <td>${image.date}</td>
                        <td>
                            <#if image.tags?has_content>
                                <#list image.tags as tag>
                                    ${tag}<#if tag_has_next>, </#if>
                                </#list>
                            <#else>
                                No tags
                            </#if>
                        </td>
                        <td>${image.path}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    <#else>
        <p>No images found in the repository.</p>
    </#if>
</body>
</html>
