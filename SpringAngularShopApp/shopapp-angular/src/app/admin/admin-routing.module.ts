import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AdminGuardFn } from "../services/auth.guard";
import { AdminComponent } from "./admin/admin.component";
import { OrderAdminComponent } from "./order-admin/order-admin.component";
import { OrderDetailAdminComponent } from "./order-detail-admin/order-detail-admin.component";
import { ProductAdminComponent } from "./product-admin/product-admin.component";
import { CategoryAdminComponent } from "./category-admin/category-admin.component";

const routes: Routes = [
  {
    path: 'admin',
    canActivate: [AdminGuardFn],
    children: [
      { path: 'orders', component: OrderAdminComponent },
      { path: 'orders/:id', component: OrderDetailAdminComponent },
      { path: 'products', component: ProductAdminComponent },
      { path: 'categories', component: CategoryAdminComponent },
      { path: '', component: AdminComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
