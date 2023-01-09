using Dapper;
using Microsoft.Extensions.Configuration;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Threading.Tasks;

namespace DataAccess.DbAccess
{
    public class SqlDataAccess : IDataAccess
    {
        public IConfiguration _configuration;
        public SqlDataAccess(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        public async Task<IEnumerable<T>> LoadData<T, U>(
            string storedProcedure,
            U parameters,
            string connentionName = "Default")
        {
            using IDbConnection connection = new SqlConnection(_configuration.GetConnectionString(connentionName));
            return await connection.QueryAsync<T>(storedProcedure, parameters, commandType: CommandType.StoredProcedure);
        }
        public async Task SaveData<T>(
           string storedProcedure,
            T parameters,
            string connentionName = "Default")
        {
            using IDbConnection connection = new SqlConnection(_configuration.GetConnectionString(connentionName));
            await connection.ExecuteAsync(storedProcedure, parameters, commandType: CommandType.StoredProcedure);
        }
    }
}
