CREATE PROCEDURE [dbo].[spUser_Delete]
	@Id int 
AS
BEGIN
	delete from dbo.[User] where Id = @Id;
END