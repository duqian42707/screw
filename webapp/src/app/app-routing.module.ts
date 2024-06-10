import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {GenerateComponent} from "./pages/generate/generate.component";
import {DatasourceComponent} from "./pages/datasource/datasource.component";
import {DocsComponent} from "./pages/docs/docs.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: '/datasource'},
  {path: 'datasource', component: DatasourceComponent},
  {path: 'generate', component: GenerateComponent},
  {path: 'docs', component: DocsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
