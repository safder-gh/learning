using DataAccess.Model;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DataAccess.Data
{
    public interface IData
    {
        Task Delete(int id);
        Task<UserModel> Get(int id);
        Task<IEnumerable<UserModel>> GetAll();
        Task Insert(UserModel user);
        Task Update(UserModel user);
    }
}