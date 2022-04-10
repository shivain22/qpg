import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { CategoryMasterDetailComponent } from 'app/entities/category-master/category-master-detail.component';
import { CategoryMaster } from 'app/shared/model/category-master.model';

describe('Component Tests', () => {
  describe('CategoryMaster Management Detail Component', () => {
    let comp: CategoryMasterDetailComponent;
    let fixture: ComponentFixture<CategoryMasterDetailComponent>;
    const route = ({ data: of({ categoryMaster: new CategoryMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [CategoryMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoryMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoryMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoryMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoryMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
