using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace eTickets.Data.Base
{
    public class EntityBaseRepository<T> : IEntityBaseRepository<T> where T : class, IEntityBasey, new()
    {
        private readonly AppDbContext appDbContext;
        public EntityBaseRepository(AppDbContext appDbContext)
        {
            this.appDbContext = appDbContext;
        }
        public async Task AddAsync(T entity) => await this.appDbContext.AddAsync(entity);
        public async Task DeleteAsync(Guid Id)
        {
            var entity = await appDbContext.Set<T>().FirstOrDefaultAsync(e => e.Id == Id);
            EntityEntry entityEntry = appDbContext.Entry<T>(entity);
            entityEntry.State = EntityState.Deleted;
            await appDbContext.SaveChangesAsync();
        }

        public async Task<IEnumerable<T>> GetAllAsync() => await appDbContext.Set<T>().ToListAsync();

        public async Task<IEnumerable<T>> GetAllAsync(params Expression<Func<T, object>>[] includeProperties)
        {
            IQueryable<T> query = appDbContext.Set<T>();
            query = includeProperties.Aggregate(query, (current, includeProperties) => current.Include(includeProperties));
            return await query.ToListAsync();
        }

        public async Task<T> GetByIdAsync(Guid Id) => await appDbContext.Set<T>().FirstOrDefaultAsync(e => e.Id == Id);

        public async Task UpdateAsync(T entity)
        {
            EntityEntry entityEntry = appDbContext.Entry<T>(entity);
            entityEntry.State = EntityState.Modified;
            await appDbContext.SaveChangesAsync();
        }
    }
}
