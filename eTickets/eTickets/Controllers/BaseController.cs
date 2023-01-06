using eTickets.Data;
using Microsoft.AspNetCore.Mvc;

namespace eTickets.Controllers
{
    public abstract class BaseController : Controller
    {
        protected readonly AppDbContext _context;
        public BaseController(AppDbContext context)
        {
            _context = context;
        }
    }
}
