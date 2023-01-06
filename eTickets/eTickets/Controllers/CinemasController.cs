using eTickets.Data;
using eTickets.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;

namespace eTickets.Controllers
{
    public class CinemasController : BaseController
    {
        private readonly DbSet<Cinema> models;
        public CinemasController(AppDbContext context) : base(context)
        {
            models = context.Cinemas;
        }
        public async Task<IActionResult> Index()
        {
            var data = await models.ToListAsync();
            return View(data);
        }
    }
}
