<%@ Page language="c#" Codebehind="bat.aspx.cs" AutoEventWireup="false" trace="False"
Inherits="batsmen.BattingPage" %>

<P>
    Match id:
    <asp:label id="matchLabel" Text="<%# match %>" runat="server" font-bold="True">
    </asp:label>&nbsp;
</P>

<P>
    <asp:label id=teamLabel Text="<%# team %>" runat="server" font-bold="True">
    </asp:label>&nbsp;
    <asp:Label id=inningsLabel Text="<%# ordinalInnings %>" runat="server">
    </asp:Label>&nbsp;innings</P>
<P>

<asp:DataGrid id="DataGrid1" runat="server" Width="480px" Height="171px"
    BorderColor="#336666" BorderStyle="Double" BorderWidth="3px" BackColor="White"
    CellPadding="4" GridLines="Horizontal" AutoGenerateColumns="False">

    <SelectedItemStyle Font-Bold="True" ForeColor="White" BackColor="#339966"></
        SelectedItemStyle>

    <ItemStyle ForeColor="#333333" BackColor="White"></ItemStyle>
    <HeaderStyle Font-Bold="True" ForeColor="White" BackColor="#336666"></HeaderStyle>
    <FooterStyle ForeColor="#333333" BackColor="White"></FooterStyle>

    <Columns>
        <asp:BoundColumn DataField="name" HeaderText="Batsman">
            <HeaderStyle Width="70px"></HeaderStyle>
        </asp:BoundColumn>
        <asp:BoundColumn DataField="runs" HeaderText="Runs">
            <HeaderStyle Width="30px"></HeaderStyle>
        </asp:BoundColumn>
        <asp:BoundColumn DataField="rateString" HeaderText="Rate">
            <HeaderStyle Width="30px"></HeaderStyle>
        </asp:BoundColumn>
    </Columns>

    <PagerStyle HorizontalAlign="Center" ForeColor="White" BackColor="#336666"
        Mode="NumericPages"></PagerStyle>
</asp:DataGrid></P>