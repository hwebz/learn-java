import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminComponent } from './admin/admin.component';
import { CategoryAdminComponent } from './category-admin/category-admin.component';
import { OrderAdminComponent } from './order-admin/order-admin.component';
import { OrderDetailAdminComponent } from './order-detail-admin/order-detail-admin.component';
import { ProductAdminComponent } from './product-admin/product-admin.component';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminHeaderComponent } from '../components/admin-header/admin-header.component';

@NgModule({
  declarations: [
    AdminComponent,
    CategoryAdminComponent,
    OrderAdminComponent,
    OrderDetailAdminComponent,
    ProductAdminComponent,
    AdminHeaderComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule
  ]
})
export class AdminModule { }
