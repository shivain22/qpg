import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { QpgSharedModule } from 'app/shared/shared.module';
import { QpgCoreModule } from 'app/core/core.module';
import { QpgAppRoutingModule } from './app-routing.module';
import { QpgHomeModule } from './home/home.module';
import { QpgEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    QpgSharedModule,
    QpgCoreModule,
    QpgHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    QpgEntityModule,
    QpgAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class QpgAppModule {}
