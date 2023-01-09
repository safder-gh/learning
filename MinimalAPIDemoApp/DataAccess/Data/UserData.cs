using DataAccess.DbAccess;
using DataAccess.Model;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DataAccess.Data
{
    public class UserData : IData
    {
        private readonly IDataAccess _db;

        public UserData(IDataAccess db)
        {
            _db = db;
        }
        public Task<IEnumerable<UserModel>> GetAll() => _db.LoadData<UserModel, dynamic>(storedProcedure: "dbo.spUser_GetAll", new { });
        public async Task<UserModel?> Get(int id)
        {
            var results = await _db.LoadData<UserModel, dynamic>(storedProcedure: "dbo.spUser_Get", new { Id = id });
            return results.FirstOrDefault();
        }
        public Task Insert(UserModel user) => _db.SaveData(storedProcedure: "dbo.spUser_Insert", new { user.FirstName, user.LastName });
        public Task Update(UserModel user) => _db.SaveData(storedProcedure: "dbo.spUser_Update", user);

        public Task Delete(int id) => _db.SaveData(storedProcedure: "dbo.spUser_Delete", new { Id = id });
    }
}
