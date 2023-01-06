CREATE PROCEDURE [dbo].[spUser_Get]
	@Id int 
AS
BEGIN
	select * from dbo.[User] where Id = @Id;
END