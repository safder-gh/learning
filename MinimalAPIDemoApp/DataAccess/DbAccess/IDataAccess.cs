using System.Collections.Generic;
using System.Threading.Tasks;

namespace DataAccess.DbAccess
{
    public interface IDataAccess
    {
        Task<IEnumerable<T>> LoadData<T, U>(string storedProcedure, U parameters, string connentionName = "Default");
        Task SaveData<T>(string storedProcedure, T parameters, string connentionName = "Default");
    }
}