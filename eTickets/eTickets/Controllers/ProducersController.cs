using eTickets.Data.Services;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Threading.Tasks;

namespace eTickets.Controllers
{
    public class ProducersController : Controller
    {
        private readonly IProducersService service;
        public ProducersController(IProducersService service)
        {
            this.service = service;
        }
        public async Task<IActionResult> Index()
        {
            var data = await service.GetAllAsync();
            return View(data);
        }

        public async Task<IActionResult> Details(Guid id)
        {
            var entity = await service.GetByIdAsync(id);
            if (entity == null) return View("NotFound");
            return View(entity);
        }
    }
}
