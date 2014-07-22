<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: tan
  Date: 14-7-20
  Time: 下午11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
  <form action="subscribe.action" name="subscribe" method="post">
      <table>
          <tr><td style="color: red">${tip}</td></tr>
          <tr>
              <td><input type="text" name="mobile" size="20"></td>
          </tr>

          <tr>
              <td><input type="submit" value="订购"></td>
          </tr>
      </table>


  </form>
  </body>
</html>
