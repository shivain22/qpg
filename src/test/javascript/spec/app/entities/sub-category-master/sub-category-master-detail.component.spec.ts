import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { SubCategoryMasterDetailComponent } from 'app/entities/sub-category-master/sub-category-master-detail.component';
import { SubCategoryMaster } from 'app/shared/model/sub-category-master.model';

describe('Component Tests', () => {
  describe('SubCategoryMaster Management Detail Component', () => {
    let comp: SubCategoryMasterDetailComponent;
    let fixture: ComponentFixture<SubCategoryMasterDetailComponent>;
    const route = ({ data: of({ subCategoryMaster: new SubCategoryMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [SubCategoryMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SubCategoryMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubCategoryMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load subCategoryMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subCategoryMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
